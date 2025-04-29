package com.mumu.mumu.service;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.dto.EduListResponseDto;

import java.util.List;

public interface EduListService {
    List<Edu> getEduList(String region, String field, String status);

    EduListResponseDto getEduById(Long eduId);
}
