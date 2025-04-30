package com.mumu.mumu.controller;

import com.mumu.mumu.dto.SpaceDetailResponseDto;
import com.mumu.mumu.dto.SpaceListResponseDto;
import com.mumu.mumu.service.SpaceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String access_token) {
        List<String> regions = parseToList(region);
        List<String> targets = parseToList(target);
        List<String> types = parseToList(type);

        return spaceListService.getSpaceList(regions, targets, types, access_token);
    }

    @GetMapping("/api/space/{spaceId}")
    public SpaceDetailResponseDto getSpaceById(
            @PathVariable String spaceId,
            @RequestParam(required = false) String access_token) {
        return spaceListService.getSpaceById(spaceId, access_token);
    }

    private List<String> parseToList(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
    }
}