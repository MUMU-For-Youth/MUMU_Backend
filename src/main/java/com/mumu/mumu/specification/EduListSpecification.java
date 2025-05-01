package com.mumu.mumu.specification;

import com.mumu.mumu.domain.Edu;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EduListSpecification {

    public static Specification<Edu> hasRegion(String region) {
        return (root, query, cb) -> {
            if (region == null || region.isBlank()) return null;
            return cb.like(root.get("eduRegion"), "%" + region + "%");
        };
    }

    public static Specification<Edu> hasField(String field) {
        return (root, query, cb) -> {
            if (field == null || field.isBlank()) return null;
            return cb.equal(root.get("field"), field);
        };
    }

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

    // 오늘 이후(포함) 데이터 필터링
    public static Specification<Edu> startDateAfterOrEqual(LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(
                root.get("recruitmentStartDate"), date.toString()
        );
    }

    // 오늘 이전 데이터 필터링
    public static Specification<Edu> startDateBefore(LocalDate date) {
        return (root, query, cb) -> cb.lessThan(
                root.get("recruitmentStartDate"), date.toString()
        );
    }
}