package com.mumu.mumu.controller;

import com.mumu.mumu.dto.SpaceDetailResponseDto;
import com.mumu.mumu.dto.SpaceListResponseDto;
import com.mumu.mumu.service.SpaceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpaceListController {

    private final SpaceListService spaceListService;

    @Autowired
    public SpaceListController(SpaceListService spaceListService) {
        this.spaceListService = spaceListService;
    }

    @GetMapping("/api/space")
    public List<SpaceListResponseDto> getSpaceList(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String target,
            @RequestParam(required = false) String type) {
        return spaceListService.getSpaceList(region, target, type);
    }

    @GetMapping("/api/space/{spaceId}")
    public SpaceDetailResponseDto getSpaceById(@PathVariable String spaceId) {
        return spaceListService.getSpaceById(spaceId);
    }
}