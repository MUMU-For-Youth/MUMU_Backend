package com.mumu.mumu.service;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.dto.EduDetailResponseDto;
import com.mumu.mumu.dto.EduListResponseDto;

import java.util.List;

public interface EduListService {
    List<EduListResponseDto> getEduList(List<String> regions, List<String> fields, List<String> statuses, String accessToken);

    EduDetailResponseDto getEduById(Long eduId, String accessToken);
}
