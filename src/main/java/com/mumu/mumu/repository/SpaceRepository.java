package com.mumu.mumu.repository;

import com.mumu.mumu.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {
    List<Space> findByRegionAndSpaceTargetAndSpaceType(String region, String target, String type);
}
