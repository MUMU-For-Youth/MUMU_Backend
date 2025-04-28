package com.mumu.mumu.service;

import com.mumu.mumu.domain.Edu;

import java.util.List;

public interface EduListService {
    List<Edu> getEduList(List<String> regions, String field, String status);
}