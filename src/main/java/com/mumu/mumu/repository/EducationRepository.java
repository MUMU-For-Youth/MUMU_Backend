package com.mumu.mumu.repository;

import com.mumu.mumu.domain.Edu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Edu, Long> {
    List<Edu> findByEduRegionAndFieldAndRecruitmentStatus(String region, String field, String status);
}
