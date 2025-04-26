package com.mumu.mumu.controller;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.dto.EduListResponseDto;
import com.mumu.mumu.service.EduListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        List<Edu> eduLists = eduListService.getEduList(region, field, status);
        // EduList를 EduListResponseDto로 변환하여 반환
        return eduLists.stream()
                .map(EduListResponseDto::new)  // EduList -> EduListResponseDto로 변환
                .collect(Collectors.toList());
    }
}