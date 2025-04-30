package com.mumu.mumu.service;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.dto.EduDetailResponseDto;
import com.mumu.mumu.repository.EduListRepository;
import com.mumu.mumu.specification.EduListSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EduListServiceImpl implements EduListService {

    private final EduListRepository eduListRepository;

    public EduListServiceImpl(EduListRepository eduListRepository) {
        this.eduListRepository = eduListRepository;
    }

    @Override
    public List<Edu> getEduList(String region, String field, String status) {
        Specification<Edu> spec = Specification.where(null);

        if (region != null && !region.isBlank()) {
            spec = spec.and(EduListSpecification.hasRegion(region));
        }
        if (field != null && !field.isBlank()) {
            spec = spec.and(EduListSpecification.hasField(field));
        }
        if (status != null && !status.isBlank()) {
            spec = spec.and(EduListSpecification.hasStatus(status));
        }

        LocalDate today = LocalDate.now();

        // 미래 데이터 (오늘 이후 포함) : 날짜 오름차순 → 이름 오름차순
        List<Edu> futureList = eduListRepository.findAll(
                spec.and(EduListSpecification.startDateAfterOrEqual(today)),
                Sort.by(
                        Sort.Order.asc("recruitmentStartDate"),
                        Sort.Order.asc("eduName")
                )
        );

        // 과거 데이터 (오늘 이전) : 날짜 내림차순 → 이름 오름차순
        List<Edu> pastList = eduListRepository.findAll(
                spec.and(EduListSpecification.startDateBefore(today)),
                Sort.by(
                        Sort.Order.desc("recruitmentStartDate"),
                        Sort.Order.asc("eduName")
                )
        );

        // 미래 + 과거 합치기
        List<Edu> result = new ArrayList<>(futureList.size() + pastList.size());
        result.addAll(futureList);
        result.addAll(pastList);

        return result;
    }

    // edu_id로 단건 조회
    @Override
    public EduDetailResponseDto getEduById(Long eduId) {
        Edu edu = eduListRepository.findById(eduId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교육이 존재하지 않습니다. id=" + eduId));
        return new EduDetailResponseDto(edu);
    }
}