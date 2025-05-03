package com.mumu.mumu.service;

import com.mumu.mumu.domain.EduBookmark;
import com.mumu.mumu.domain.SpaceBookmark;
import com.mumu.mumu.domain.Member;
import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.domain.Space;
import com.mumu.mumu.dto.EduBookmarkDTO;
import com.mumu.mumu.dto.EduBookmarkResponseDTO;
import com.mumu.mumu.dto.SpaceBookmarkDTO;
import com.mumu.mumu.dto.SpaceBookmarkResponseDTO;
import com.mumu.mumu.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

    private final EntityManager em;
    private final EduBookmarkRepository eduBmRepo;
    private final SpaceBookmarkRepository spaceBmRepo;
    private final MemberRepository memberRepo;
    private final EducationRepository eduRepo;
    private final SpaceRepository spaceRepo;

    /** 교육 북마크 존재 여부 조회 */
    public boolean checkEduBookmark(Long memberId, String eduId) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Edu eduRef = em.getReference(Edu.class, eduId);
        return eduBmRepo.findByMemberAndEdu(member, eduRef).isPresent();
    }

    /** 교육 북마크 정보 조회 */
    public List<EduBookmarkResponseDTO> getEduBookmarkDetails(Long memberId, String eduId) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        if (eduId != null) {
            // 특정 edu_id에 대한 북마크 정보 조회
            Edu edu = eduRepo.findById(Long.parseLong(eduId))
                    .orElseThrow(() -> new IllegalArgumentException("교육 정보 없음"));

            return eduBmRepo.findByMemberAndEdu(member, edu)
                    .map(bookmark -> List.of(convertToEduBookmarkResponseDTO(edu)))
                    .orElse(List.of());
        } else {
            // 해당 회원의 모든 교육 북마크 정보 조회
            List<EduBookmark> bookmarks = eduBmRepo.findByMember(member);
            return bookmarks.stream()
                    .map(bookmark -> convertToEduBookmarkResponseDTO(bookmark.getEdu()))
                    .collect(Collectors.toList());
        }
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

    /** 공간 북마크 정보 조회 */
    public List<SpaceBookmarkResponseDTO> getSpaceBookmarkDetails(Long memberId, String spaceId) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        if (spaceId != null) {
            // 특정 space_id에 대한 북마크 정보 조회
            Space space = spaceRepo.findById(spaceId)
                    .orElseThrow(() -> new IllegalArgumentException("공간 정보 없음"));

            return spaceBmRepo.findByMemberAndSpace(member, space)
                    .map(bookmark -> List.of(convertToSpaceBookmarkResponseDTO(space)))
                    .orElse(List.of());
        } else {
            // 해당 회원의 모든 공간 북마크 정보 조회
            List<SpaceBookmark> bookmarks = spaceBmRepo.findByMember(member);
            return bookmarks.stream()
                    .map(bookmark -> convertToSpaceBookmarkResponseDTO(bookmark.getSpace()))
                    .collect(Collectors.toList());
        }
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
    private EduBookmarkResponseDTO convertToEduBookmarkResponseDTO(Edu edu) {
        return new EduBookmarkResponseDTO(
                edu.getEduId(),
                edu.getField(),
                edu.getEduName(),
                edu.getEduContent(),
                edu.getEduMethod(),
                edu.getMaxCapacity(),
                edu.getRecruitmentStartDate(),
                edu.getRecruitmentEndDate(),
                edu.getRecruitmentStartTime(),
                edu.getRecruitmentEndTime(),
                edu.getEduLocationName(),
                edu.getEduAddress(),
                edu.getEduLocationLongitude(),
                edu.getEduLocationLatitude(),
                edu.getEduTarget(),
                edu.getEduUrl(),
                edu.getEduImage(),
                edu.getEduFee(),
                edu.getRecruitmentStatus(),
                edu.getEduTeacher(),
                edu.getEduStartDate(),
                edu.getEduEndDate(),
                edu.getEduStartTime(),
                edu.getEduEndTime(),
                edu.getEduRegion()
        );
    }
    private SpaceBookmarkResponseDTO convertToSpaceBookmarkResponseDTO(Space space) {
        return new SpaceBookmarkResponseDTO(
                space.getSpaceId(),
                space.getSpaceName(),
                space.getSpaceLocation(),
                space.getSpaceLocationLongitude(),
                space.getSpaceLocationLatitude(),
                space.getSpaceType(),
                space.getSpaceTarget(),
                space.getSpaceUrl(),
                space.getSpaceImage(),
                space.getRegion(),
                space.getContactNumber(),
                space.getOpeningTime() != null ? space.getOpeningTime().toString() : null,
                space.getClosingTime() != null ? space.getClosingTime().toString() : null,
                space.getServiceStartDate() != null ? space.getServiceStartDate().toString() : null,
                space.getServiceEndDate() != null ? space.getServiceEndDate().toString() : null,
                space.getRsvStartDate() != null ? space.getRsvStartDate().toString() : null,
                space.getRsvEndDate() != null ? space.getRsvEndDate().toString() : null,
                space.getDetail(),
                space.getSpaceFee(),
                space.getSpaceStatus()

        );
    }
}
