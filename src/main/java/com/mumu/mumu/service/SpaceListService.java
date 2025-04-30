package com.mumu.mumu.service;

import com.mumu.mumu.domain.Space;
import com.mumu.mumu.dto.SpaceDetailResponseDto;
import com.mumu.mumu.dto.SpaceListResponseDto;

import java.util.List;

public interface SpaceListService {

    List<SpaceListResponseDto> getSpaceList(List<String> regions, List<String> targets, List<String> types);

    SpaceDetailResponseDto getSpaceById(String spaceId);
}