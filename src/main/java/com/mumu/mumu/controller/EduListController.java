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
            @RequestParam(required = false) String status
    ) {
        List<String> regions = parseToList(region);
        List<String> fields = parseToList(field);
        List<String> statuses = parseToList(status);

        List<Edu> eduLists = eduListService.getEduList(regions, fields, statuses);
        // EduList를 EduListResponseDto로 변환하여 반환
        return eduLists.stream()
                .map(EduListResponseDto::new)  // EduList -> EduListResponseDto로 변환
                .collect(Collectors.toList());
    }

    @GetMapping("/{eduId}")
    public EduDetailResponseDto getEduById(@PathVariable Long eduId) {
        return eduListService.getEduById(eduId);
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