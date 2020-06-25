package com.avito.controllers.rest.kladr_rest;


import com.avito.models.kladr.City;
import com.avito.service.interfaces.CityService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest/kladr/city")
@AllArgsConstructor
public class CityRestController {
    private static final Logger logger = LoggerFactory.getLogger(CityRestController.class);

    private CityService cityService;

    @GetMapping("/allCities")
    public List<City> getAllCities() {
        return cityService.findAll();
    }

    @PostMapping
    public List<City> getCitiesByName(String name) {
        return cityService.findAllByName(name);
    }

    @PostMapping("/regionCities")
    public List<City> getCitiesByRegionId(Long regionId) {
        return cityService.findAllByRegionId(regionId);
    }

}
