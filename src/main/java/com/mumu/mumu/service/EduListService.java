package com.mumu.mumu.service;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.dto.EduDetailResponseDto;

import java.util.List;

public interface EduListService {
    List<Edu> getEduList(List<String> regions, List<String> fields, List<String> statuses);

    EduDetailResponseDto getEduById(Long eduId);
}
