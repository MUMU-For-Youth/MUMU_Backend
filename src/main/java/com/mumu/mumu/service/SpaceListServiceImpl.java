package com.mumu.mumu.service;

import com.mumu.mumu.domain.Space;
import com.mumu.mumu.dto.SpaceDetailResponseDto;
import com.mumu.mumu.dto.SpaceListResponseDto;
import com.mumu.mumu.repository.SpaceRepository;
import com.mumu.mumu.specification.SpaceListSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceListServiceImpl implements SpaceListService {

    private final SpaceRepository spaceRepository;

    public SpaceListServiceImpl(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    @Override
    public List<SpaceListResponseDto> getSpaceList(String region, String target, String type) {
        Specification<Space> spec = Specification.where(null);

        if (region != null && !region.isBlank()) {
            spec = spec.and(SpaceListSpecification.hasRegion(region));
        }
        if (target != null && !target.isBlank()) {
            spec = spec.and(SpaceListSpecification.hasTarget(target));
        }
        if (type != null && !type.isBlank()) {
            spec = spec.and(SpaceListSpecification.hasType(type));
        }

        LocalDate today = LocalDate.now();

        // 미래 데이터 (오늘 이후 포함) : 날짜 오름차순 → 이름 오름차순
        List<Space> futureList = spaceRepository.findAll(
                spec.and(SpaceListSpecification.serviceStartDateAfterOrEqual(today)),
                Sort.by(
                        Sort.Order.asc("serviceStartDate"),
                        Sort.Order.asc("spaceName")
                )
        );

        // 과거 데이터 (오늘 이전) : 날짜 내림차순 → 이름 오름차순
        List<Space> pastList = spaceRepository.findAll(
                spec.and(SpaceListSpecification.serviceStartDateBefore(today)),
                Sort.by(
                        Sort.Order.desc("serviceStartDate"),
                        Sort.Order.asc("spaceName")
                )
        );

        // 미래 + 과거 합치기
        List<Space> result = new ArrayList<>(futureList.size() + pastList.size());
        result.addAll(futureList);
        result.addAll(pastList);

        // Entity를 DTO로 변환하여 반환
        List<SpaceListResponseDto> dtoList = new ArrayList<>();
        for (Space space : result) {
            dtoList.add(new SpaceListResponseDto(space));
        }

        return dtoList;
    }

    @Override
    public SpaceDetailResponseDto getSpaceById(String spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공간이 존재하지 않습니다. id=" + spaceId));
        return new SpaceDetailResponseDto(space);
    }
}