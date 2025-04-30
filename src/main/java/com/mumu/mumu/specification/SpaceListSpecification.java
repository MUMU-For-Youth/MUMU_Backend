package com.mumu.mumu.specification;

import com.mumu.mumu.domain.Space;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class SpaceListSpecification {

    // region 조건
    public static Specification<Space> hasRegion(String region) {
        return (root, query, builder) -> builder.equal(root.get("region"), region);
    }

    // target 조건
    public static Specification<Space> hasTarget(String target) {
        return (root, query, builder) -> builder.equal(root.get("spaceTarget"), target);
    }

    // type 조건
    public static Specification<Space> hasType(String type) {
        return (root, query, builder) -> builder.equal(root.get("spaceType"), type);
    }

    // 서비스 시작일이 오늘 이후인 경우
    public static Specification<Space> serviceStartDateAfterOrEqual(LocalDate date) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("serviceStartDate"), date);
    }

    // 서비스 시작일이 오늘 이전인 경우
    public static Specification<Space> serviceStartDateBefore(LocalDate date) {
        return (root, query, builder) -> builder.lessThan(root.get("serviceStartDate"), date);
    }
}