package com.mumu.mumu.specification;

import com.mumu.mumu.domain.Space;
import com.mumu.mumu.domain.SpaceTarget;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceListSpecification {

    // region 조건
    public static Specification<Space> hasRegion(String region) {
        return (root, query, builder) -> builder.equal(root.get("region"), region);
    }

    // target 조건
    public static Specification<Space> hasTarget(String target) {
        return (root, query, builder) -> {
            SpaceTarget spaceTarget = SpaceTarget.fromDisplayName(target);
            if (spaceTarget == null) {
                return null;
            }
            
            List<String> targetValues = spaceTarget.getTargetValues();
            return builder.or(
                targetValues.stream()
                    .map(value -> builder.like(root.get("spaceTarget"), "%" + value + "%"))
                    .toArray(jakarta.persistence.criteria.Predicate[]::new)
            );
        };
    }

    // 여러 target 조건을 OR로 처리
    public static Specification<Space> hasTargets(List<String> targets) {
        if (targets == null || targets.isEmpty()) {
            return null;
        }
        return Specification.where(
            targets.stream()
                .map(SpaceListSpecification::hasTarget)
                .filter(spec -> spec != null)
                .reduce(null, (spec1, spec2) -> {
                    if (spec1 == null) return spec2;
                    if (spec2 == null) return spec1;
                    return spec1.or(spec2);
                })
        );
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