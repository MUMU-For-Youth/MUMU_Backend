package com.mumu.mumu.repository;

import com.mumu.mumu.domain.SpaceBookmark;
import com.mumu.mumu.domain.Member;
import com.mumu.mumu.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpaceBookmarkRepository extends JpaRepository<SpaceBookmark, Long> {

    Optional<SpaceBookmark> findByMemberAndSpace(Member member, Space space);
}
