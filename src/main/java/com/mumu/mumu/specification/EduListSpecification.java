package com.mumu.mumu.specification;

import com.mumu.mumu.domain.Edu;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EduListSpecification {

    public static Specification<Edu> hasRegion(String region) {
        return (root, query, cb) -> {
            if (region == null || region.isBlank()) return null;
            return cb.like(root.get("eduAddress"), "%" + region + "%");
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
            if (status == null || !status.equalsIgnoreCase("ongoing")) return null;
            LocalDate today = LocalDate.now();
            return cb.and(
                    cb.lessThanOrEqualTo(root.get("eduStartDate"), today),
                    cb.greaterThanOrEqualTo(root.get("eduEndDate"), today)
            );
        };
    }
}