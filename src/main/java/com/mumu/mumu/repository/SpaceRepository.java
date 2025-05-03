package com.mumu.mumu.repository;

import com.mumu.mumu.domain.Space;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String>, JpaSpecificationExecutor<Space> {
    default List<Space> findByRegionAndSpaceTargetAndType(String region, String target, String type) {
        Specification<Space> spec = Specification.where(null);
        
        if (region != null && !region.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("region"), region));
        }
        if (target != null && !target.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("spaceTarget"), target));
        }
        if (type != null && !type.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), type));
        }
        
        return findAll(spec);
    }
}
