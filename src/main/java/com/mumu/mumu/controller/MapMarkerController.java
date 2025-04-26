package com.mumu.mumu.controller;

import com.mumu.mumu.dto.EduMarkerDTO;
import com.mumu.mumu.dto.SpaceMarkerDTO;
import com.mumu.mumu.service.MapMarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MapMarkerController {

    private final MapMarkerService mapMarkerService;

    @GetMapping("/edu/marker")
    public List<EduMarkerDTO> getEduMarkers(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String status
    ) {
        List<String> regions = parseToList(region);
        List<String> fields = parseToList(field);
        List<String> statuses = parseToList(status);

        return mapMarkerService.getEduMarkers(regions, fields, statuses);
    }

    @GetMapping("/space/marker")
    public List<SpaceMarkerDTO> getSpaceMarkers(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String target,
            @RequestParam(required = false) String type
    ) {
        List<String> regions = parseToList(region);
        List<String> targets = parseToList(target);
        List<String> types = parseToList(type);

        return mapMarkerService.getSpaceMarkers(regions, targets, types);
    }

    private List<String> parseToList(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Arrays.stream(
                        value.split(","))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
    }
}