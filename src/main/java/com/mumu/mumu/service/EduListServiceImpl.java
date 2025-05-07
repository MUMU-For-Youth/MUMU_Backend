package com.mumu.mumu.service;

import com.mumu.mumu.domain.Edu;
import com.mumu.mumu.domain.EduBookmark;
import com.mumu.mumu.domain.Member;
import com.mumu.mumu.dto.EduDetailResponseDto;
import com.mumu.mumu.dto.EduListResponseDto;
import com.mumu.mumu.repository.EduBookmarkRepository;
import com.mumu.mumu.repository.EduListRepository;
import com.mumu.mumu.repository.MemberRepository;
import com.mumu.mumu.specification.EduListSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EduListServiceImpl implements EduListService {

    private final EduListRepository eduListRepository;
    private final EduBookmarkRepository eduBookmarkRepository;
    private final MemberRepository memberRepository;

    public EduListServiceImpl(EduListRepository eduListRepository,
                            EduBookmarkRepository eduBookmarkRepository,
                            MemberRepository memberRepository) {
        this.eduListRepository = eduListRepository;
        this.eduBookmarkRepository = eduBookmarkRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<EduListResponseDto> getEduList(List<String> regions, List<String> fields, List<String> statuses, String accessToken) {
        Specification<Edu> spec = Specification.where(null);
        if (regions != null && !regions.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("eduRegion").in(regions));
        }
        if (fields != null && !fields.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("field").in(fields));
        }
        if (statuses != null && !statuses.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("recruitmentStatus").in(statuses));
        }

        LocalDate today = LocalDate.now();

        // 미래 데이터 (오늘 이후 포함) : 날짜 오름차순 → 이름 오름차순
        List<Edu> futureList = eduListRepository.findAll(
                spec.and(EduListSpecification.startDateAfterOrEqual(today)),
                Sort.by(
                        Sort.Order.asc("recruitmentStartDate"),
                        Sort.Order.asc("eduName")
                )
        );

        // 과거 데이터 (오늘 이전) : 날짜 내림차순 → 이름 오름차순
        List<Edu> pastList = eduListRepository.findAll(
                spec.and(EduListSpecification.startDateBefore(today)),
                Sort.by(
                        Sort.Order.desc("recruitmentStartDate"),
                        Sort.Order.asc("eduName")
                )
        );

        // 미래 + 과거 합치기
        List<Edu> result = new ArrayList<>(futureList.size() + pastList.size());
        result.addAll(futureList);
        result.addAll(pastList);

        // 북마크 상태 확인
        Member member = null;
        if (accessToken != null && !accessToken.isBlank()) {
            member = memberRepository.findByAccessToken(accessToken).orElse(null);
        }

        // Entity를 DTO로 변환하여 반환
        List<EduListResponseDto> dtoList = new ArrayList<>();
        for (Edu edu : result) {
            boolean isBookmarked = false;
            if (member != null) {
                isBookmarked = checkEduBookmark(member.getMemberId(), edu.getEduId());
            }
            dtoList.add(new EduListResponseDto(edu, isBookmarked));
        }

        return dtoList;
    }

    // edu_id로 단건 조회
    @Override
    public EduDetailResponseDto getEduById(Long eduId, String accessToken) {
        Edu edu = eduListRepository.findById(eduId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교육이 존재하지 않습니다. id=" + eduId));
        
        boolean isBookmarked = false;
        if (accessToken != null && !accessToken.isBlank() && !"null".equals(accessToken)) {
            Member member = memberRepository.findByAccessToken(accessToken)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 access_token입니다."));
            isBookmarked = checkEduBookmark(member.getMemberId(), eduId);
        }
        
        return new EduDetailResponseDto(edu, isBookmarked);
    }

    private boolean checkEduBookmark(Long memberId, Long eduId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. id=" + memberId));
        
        Edu edu = eduListRepository.findById(eduId)
                .orElseThrow(() -> new IllegalArgumentException("교육이 존재하지 않습니다. id=" + eduId));
        
        return eduBookmarkRepository.findByMemberAndEdu(member, edu).isPresent();
    }
}