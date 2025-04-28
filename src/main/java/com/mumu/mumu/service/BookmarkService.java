package com.mumu.mumu.service;

import com.mumu.mumu.domain.EduBookmark;
import com.mumu.mumu.domain.SpaceBookmark;
import com.mumu.mumu.domain.Member;
import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.domain.Space;
import com.mumu.mumu.dto.EduBookmarkDTO;
import com.mumu.mumu.dto.SpaceBookmarkDTO;
import com.mumu.mumu.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

    private final EntityManager em;                    // ★ 추가
    private final EduBookmarkRepository eduBmRepo;
    private final SpaceBookmarkRepository spaceBmRepo;
    private final MemberRepository memberRepo;

    /** 교육 북마크 존재 여부 조회 */
    public boolean checkEduBookmark(Long memberId, String eduId) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Edu eduRef = em.getReference(Edu.class, eduId);   // DB hit X
        return eduBmRepo.findByMemberAndEdu(member, eduRef).isPresent();
    }

    /** 교육 북마크 ON/OFF 토글 */
    public void toggleEduBookmark(Long memberId, EduBookmarkDTO dto) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Edu eduRef = em.getReference(Edu.class, dto.getEduId());

        eduBmRepo.findByMemberAndEdu(member, eduRef)
                .ifPresentOrElse(
                        eduBmRepo::delete,
                        () -> eduBmRepo.save(
                                EduBookmark.builder()
                                        .member(member)
                                        .edu(eduRef)
                                        .build()
                        )
                );
    }

    /** 공간 북마크 존재 여부 조회 */
    public boolean checkSpaceBookmark(Long memberId, String spaceId) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Space spaceRef = em.getReference(Space.class, spaceId);
        return spaceBmRepo.findByMemberAndSpace(member, spaceRef).isPresent();
    }

    /** 공간 북마크 ON/OFF 토글 */
    public void toggleSpaceBookmark(Long memberId, SpaceBookmarkDTO dto) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Space spaceRef = em.getReference(Space.class, dto.getSpaceId());

        spaceBmRepo.findByMemberAndSpace(member, spaceRef)
                .ifPresentOrElse(
                        spaceBmRepo::delete,
                        () -> spaceBmRepo.save(
                                SpaceBookmark.builder()
                                        .member(member)
                                        .space(spaceRef)
                                        .build()
                        )
                );
    }
}
