package com.avito.controllers.rest;

import com.avito.configs.security.AuthProvider;
import com.avito.models.Category;
import com.avito.models.Images;
import com.avito.models.Posting;
import com.avito.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/")
public class HeaderRestController {
    private static final Logger logger = LoggerFactory.getLogger(HeaderRestController.class);

    @GetMapping("/getListCategory")
    public ResponseEntity<Category> getListOfCategory() {
        Category c1 = new Category("AVTO", Collections.emptySet());
        Category c2 = new Category("AVTO1", Collections.emptySet());
        Category c3 = new Category("AVTO2", Collections.emptySet());
        Category c4 = new Category("AVTOvvdsc", Collections.emptySet());
        Category c5 = new Category("AVTOdvdf23c", Collections.emptySet());
        Category c6 = new Category("AVTdc32f", Collections.emptySet());
        Category c7 = new Category("AVTOdcasd", Collections.emptySet());
        Category c8 = new Category("AVTOadf", Collections.emptySet());
        Set<Category> list2 = new HashSet<>();
        list2.add(c1);
        list2.add(c2);
        list2.add(c3);
        list2.add(c4);
        list2.add(c5);
        list2.add(c6);
        list2.add(c7);
        list2.add(c8);
        Category category1 = new Category("AVTO", list2);
        Category category2 = new Category("Mebel", list2);
        Category category3 = new Category("Dom", list2);
        Category category4 = new Category("Java", list2);
        Set<Category> list = new HashSet<>();
        list.add(category1);
        list.add(category2);
        list.add(category3);
        list.add(category4);
        Category category0 = new Category("ALL", list);
        return new ResponseEntity<>(category0, HttpStatus.OK);
    }

    @GetMapping("/avitoNavigation")
    public ResponseEntity<List<String>> getAvitoNavigation() {
        List<String> list = new ArrayList<>();
        list.add("Объявления");
        list.add("Магазины");
        list.add("Бизнес");
        list.add("Бизнес");
        list.add("Помощь");
        return !list.isEmpty()
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

}
