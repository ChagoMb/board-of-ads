package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.kladr.Region;

import java.util.List;

public interface RegionService {

    Region findById(Long id);

    List<Region> getAllRegions();

    List<Region> findByName(String name);

    Region findRegionByName (String name);
}
