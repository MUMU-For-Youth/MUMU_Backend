package com.mumu.mumu.service;

import com.mumu.mumu.domain.Space;
import com.mumu.mumu.dto.SpaceDetailResponseDto;
import com.mumu.mumu.dto.SpaceListResponseDto;

import java.util.List;

public interface SpaceListService {

    List<SpaceListResponseDto> getSpaceList(String region, String target, String type);

    SpaceDetailResponseDto getSpaceById(String spaceId);
}