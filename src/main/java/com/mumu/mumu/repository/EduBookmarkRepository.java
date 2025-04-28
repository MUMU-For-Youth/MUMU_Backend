package com.mumu.mumu.repository;

import com.mumu.mumu.domain.EduBookmark;
import com.mumu.mumu.domain.Member;
import com.mumu.mumu.domain.Edu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EduBookmarkRepository extends JpaRepository<EduBookmark, Long> {

    Optional<EduBookmark> findByMemberAndEdu(Member member, Edu edu);
};
