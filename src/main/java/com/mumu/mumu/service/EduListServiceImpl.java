package com.mumu.mumu.service;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.repository.EduListRepository;
import com.mumu.mumu.specification.EduListSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EduListServiceImpl implements EduListService {

    private final EduListRepository eduListRepository;

    public EduListServiceImpl(EduListRepository eduListRepository) {
        this.eduListRepository = eduListRepository;
    }

    @Override
    public List<Edu> getEduList(String region, String field, String status) {
        Specification<Edu> spec = Specification.where(null);  // 시작은 null

        if (region != null && !region.isBlank()) {
            spec = spec.and(EduListSpecification.hasRegion(region));
        }
        if (field != null && !field.isBlank()) {
            spec = spec.and(EduListSpecification.hasField(field));
        }
        if (status != null && !status.isBlank()) {
            spec = spec.and(EduListSpecification.hasStatus(status));
        }

        Sort sort = Sort.by(
                Sort.Order.asc("eduStartDate"),
                Sort.Order.asc("eduName")
        );

        return eduListRepository.findAll(spec, sort);
    }
}