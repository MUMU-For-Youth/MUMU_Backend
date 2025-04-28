package com.mumu.mumu.controller;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.dto.EduListResponseDto;
import com.mumu.mumu.service.EduListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        // region을 ","로 split 해서 List로 변환
        List<String> regions = null;
        if (region != null && !region.isBlank()) {
            regions = Arrays.stream(region.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }

        List<Edu> eduLists = eduListService.getEduList(regions, field, status);

        return eduLists.stream()
                .map(EduListResponseDto::new)
                .collect(Collectors.toList());
    }
}