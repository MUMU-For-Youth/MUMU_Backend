package com.mumu.mumu.controller;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.dto.EduDetailResponseDto;
import com.mumu.mumu.dto.EduListResponseDto;
import com.mumu.mumu.service.EduListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/edu")
public class EduListController {

    private final EduListService eduListService;

    @GetMapping
    public List<EduListResponseDto> getEduLists(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String access_token
    ) {
        List<String> regions = parseToList(region);
        List<String> fields = parseToList(field);
        List<String> statuses = parseToList(status);

        return eduListService.getEduList(regions, fields, statuses, access_token);
    }

    @GetMapping("/{eduId}")
    public EduDetailResponseDto getEduById(
            @PathVariable Long eduId,
            @RequestParam(required = false) String access_token) {
        return eduListService.getEduById(eduId, access_token);
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