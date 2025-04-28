package com.mumu.mumu.specification;

import com.mumu.mumu.domain.Edu;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class EduListSpecification {

    // 여러 개의 지역(region) 값에 대해 필터링 (eduAddress가 특정 지역으로 "시작"하는지 확인)
    public static Specification<Edu> hasRegions(List<String> regions) {
        return (root, query, cb) -> {
            if (regions == null || regions.isEmpty()) return null;

            return cb.or(
                    regions.stream()
                            .map(region -> cb.like(root.get("eduAddress"), region + "%"))
                            .toArray(Predicate[]::new)  // 여기서 Predicate[]로 수정
            );
        };
    }

    // 필드(field) 필터링
    public static Specification<Edu> hasField(String field) {
        return (root, query, cb) -> {
            if (field == null || field.isBlank()) return null;
            return cb.equal(root.get("field"), field);
        };
    }

    // 모집 상태(recruitmentStatus) 필터링
    public static Specification<Edu> hasStatus(String status) {
        return (root, query, cb) -> {
            if (status == null || status.isBlank()) return null;

            if (status.equalsIgnoreCase("모집중")) {
                return cb.equal(root.get("recruitmentStatus"), "모집중");
            } else if (status.equalsIgnoreCase("모집완료")) {
                return cb.equal(root.get("recruitmentStatus"), "모집완료");
            } else if (status.equalsIgnoreCase("모집 전")) {
                return cb.equal(root.get("recruitmentStatus"), "모집전");
            } else {
                return null;
            }
        };
    }

    // 오늘 이후(포함) 모집 시작하는 데이터
    public static Specification<Edu> startDateAfterOrEqual(LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(
                root.get("recruitmentStartDate"), date.toString()
        );
    }

    // 오늘 이전 모집 시작하는 데이터
    public static Specification<Edu> startDateBefore(LocalDate date) {
        return (root, query, cb) -> cb.lessThan(
                root.get("recruitmentStartDate"), date.toString()
        );
    }
}