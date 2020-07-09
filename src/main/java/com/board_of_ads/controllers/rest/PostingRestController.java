package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.Images;
import com.board_of_ads.models.User;
import com.board_of_ads.models.kladr.City;
import com.board_of_ads.models.kladr.Region;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.RegionService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/posting")
@AllArgsConstructor
public class PostingRestController {

    private static final Logger logger = LoggerFactory.getLogger(RoleRestController.class);

    private final PostingService postingService;
    private final CategoryService categoryService;
    private final RegionService regionService;
    private final CityService cityService;

    @GetMapping("/getPostingInfo")
    public ResponseEntity<Posting> getPosting() {
        Set<Images> img = new HashSet<>();
        Posting posting = new Posting(
                "Коттедж 400 м² на участке 3 сот.",
                new Category(),
                new User(),
                "Коттедж на два хозяина. На первом этаже кухня, зал и туалет с душем, второй этаж три комнаты и туалет с ванной, третий этаж-две комнаты. Цокольный этаж с гаражом и комнатой. Готов к заселению, возможна долгосрочная аренда.",
                "Коттедж"
        );
        posting.setImagePath(img);
        posting.setPrice(123123L);
        return ResponseEntity.ok(posting);
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllPostings() {
        return ResponseEntity.ok(new Gson().toJson(postingService.getAllPostings()));
    }

    @GetMapping("/searchByCity/{cityId}")
    public ResponseEntity<List<Posting>> getPostingsByCityId(@PathVariable("cityId") String cityId) {
        return ResponseEntity.ok(postingService.getPostingsByCityId(cityId));
    }

    @GetMapping("/searchByRegion/{regionId}")
    public ResponseEntity<List<Posting>> getPostingsByRegionId(@PathVariable("regionId") String regionId) {
        return ResponseEntity.ok(postingService.getPostingsByRegionId(regionId));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> getAllPostingsForUserId(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        return ResponseEntity.ok(new Gson().toJson(postingService.getUserPostings(user)));
    }

    @GetMapping("/user/current")
    public ResponseEntity<String> getAllPostingsForCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new Gson().toJson(postingService.getUserPostings(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getPostingById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Gson().toJson(postingService.getPostingById(id)));
    }

    @GetMapping("/getProductCards")
    public ResponseEntity<List<Posting>> getProductCards() {
        Set<Images> img = new HashSet<>();
        Images images = new Images();
        images.setImagePath("/images/image-placeholder.png");
        img.add(images);
        Posting posting = new Posting();
        posting.setPrice(123123L);
        posting.setImagePath(img);
        posting.setShortDescription("Краткое описание");
        posting.setTitle("Автокресло 0-1");
        List<Posting> list = new ArrayList<>();
        for (int i = 0; i< 77; i++){
            list.add(posting);
        }
        return !list.isEmpty()
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Posting>> getSearchForm(@RequestParam String category,
                                                @RequestParam String search,
                                                @RequestParam String regionCity,
                                                 @RequestParam(required = false)String ch1,
                                                 @RequestParam(required = false) String ch2){

        Region region = null;
        City city = null;
        List<Posting> postingList = null;
        List<Posting> newPostingList = new ArrayList<>();
        List<Posting> newPostingList_1 = new ArrayList<>();
        Category category1 = null;

        if (regionCity.length() != 0){
           city = cityService.findCityByName(regionCity.split(" ")[0]);
            region = regionService.findRegionByName(regionCity.split(" ")[0]);
        }
        if (category.length() != 0){
            category1 = categoryService.findCategoryByNameRu(category);
            if (city != null){
                postingList = postingService.findAllByCategoryAndCityId(category1, city.getId().toString());
            } else {
                postingList = postingService.findAllByCategoryAndRegionId(category1, region.getId().toString());
            }
        } else {
            if (city != null){
                postingList = postingService.getPostingsByCityId(city.getName());
            } else if (region != null){
                postingList = postingService.getPostingsByRegionId(region.getName());
            }
            postingList = postingService.getAllPostings();
        }
        if (search.length() != 0) {
            if (!postingList.isEmpty()) {
                if (ch1 != null) {
                    for (Posting post : postingList) {
                        if (post.getTitle().toLowerCase().contains(search.toLowerCase())) {
                            newPostingList.add(post);
                        }
                    }
                } else {
                    for (Posting post : postingList) {
                        if (post.getFullDescription().toLowerCase().contains(search.toLowerCase())) {
                            newPostingList.add(post);
                        }
                    }
                }
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            if (!newPostingList.isEmpty()) {
                if (ch2 != null) {
                    for (Posting post : newPostingList) {
                        if (!post.getImagePath().isEmpty()) {
                            newPostingList_1.add(post);
                        }
                    }
                } else {
                    newPostingList_1.addAll(newPostingList);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            newPostingList_1.addAll(postingList);
        }

        return  new ResponseEntity<>(newPostingList_1, HttpStatus.OK);
    }

}
