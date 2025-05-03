package com.mumu.mumu.repository;

import com.mumu.mumu.domain.Edu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EduListRepository  extends JpaRepository<Edu, Long>, JpaSpecificationExecutor<Edu> {

}
