package com.board_of_ads.service.impl;

import com.board_of_ads.models.kladr.Region;
import com.board_of_ads.repository.RegionRepository;
import com.board_of_ads.service.interfaces.RegionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {
    private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

    private final RegionRepository regionRepository;

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public List<Region> findByName(String name) {
        return regionRepository.findByName(name);
    }

    @Override
    public Region findRegionByName(String name) {
        return regionRepository.findRegionByName(name);
    }
}
