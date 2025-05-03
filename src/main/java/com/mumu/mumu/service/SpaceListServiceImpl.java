package com.mumu.mumu.service;

import com.mumu.mumu.domain.Space;
import com.mumu.mumu.domain.SpaceBookmark;
import com.mumu.mumu.domain.Member;
import com.mumu.mumu.dto.SpaceDetailResponseDto;
import com.mumu.mumu.dto.SpaceListResponseDto;
import com.mumu.mumu.repository.SpaceBookmarkRepository;
import com.mumu.mumu.repository.SpaceRepository;
import com.mumu.mumu.repository.MemberRepository;
import com.mumu.mumu.specification.SpaceListSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceListServiceImpl implements SpaceListService {

    private final SpaceRepository spaceRepository;
    private final SpaceBookmarkRepository spaceBookmarkRepository;
    private final MemberRepository memberRepository;

    public SpaceListServiceImpl(SpaceRepository spaceRepository,
                              SpaceBookmarkRepository spaceBookmarkRepository,
                              MemberRepository memberRepository) {
        this.spaceRepository = spaceRepository;
        this.spaceBookmarkRepository = spaceBookmarkRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<SpaceListResponseDto> getSpaceList(List<String> regions, List<String> targets, List<String> types, String accessToken) {
        Specification<Space> spec = Specification.where(null);
        if (regions != null && !regions.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("region").in(regions));
        }
        if (targets != null && !targets.isEmpty()) {
            spec = spec.and(SpaceListSpecification.hasTargets(targets));
        }
        if (types != null && !types.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("spaceType").in(types));
        }

        LocalDate today = LocalDate.now();

        // 미래 데이터 (오늘 이후 포함) : 날짜 오름차순 → 이름 오름차순
        List<Space> futureList = spaceRepository.findAll(
                spec.and(SpaceListSpecification.serviceStartDateAfterOrEqual(today)),
                Sort.by(
                        Sort.Order.asc("serviceStartDate"),
                        Sort.Order.asc("spaceName")
                )
        );

        // 과거 데이터 (오늘 이전) : 날짜 내림차순 → 이름 오름차순
        List<Space> pastList = spaceRepository.findAll(
                spec.and(SpaceListSpecification.serviceStartDateBefore(today)),
                Sort.by(
                        Sort.Order.desc("serviceStartDate"),
                        Sort.Order.asc("spaceName")
                )
        );

        // 미래 + 과거 합치기
        List<Space> result = new ArrayList<>(futureList.size() + pastList.size());
        result.addAll(futureList);
        result.addAll(pastList);

        // 북마크 상태 확인
        Member member = null;
        if (accessToken != null && !accessToken.isBlank()) {
            member = memberRepository.findByAccessToken(accessToken).orElse(null);
        }

        // Entity를 DTO로 변환하여 반환
        List<SpaceListResponseDto> dtoList = new ArrayList<>();
        for (Space space : result) {
            boolean isBookmarked = false;
            if (member != null) {
                isBookmarked = checkSpaceBookmark(member.getMemberId(), space.getSpaceId());
            }
            dtoList.add(new SpaceListResponseDto(space, isBookmarked));
        }

        return dtoList;
    }

    @Override
    public SpaceDetailResponseDto getSpaceById(String spaceId, String accessToken) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공간이 존재하지 않습니다. id=" + spaceId));
        
        boolean isBookmarked = false;
        if (accessToken != null && !accessToken.isBlank()) {
            Member member = memberRepository.findByAccessToken(accessToken)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 access_token입니다."));
            isBookmarked = checkSpaceBookmark(member.getMemberId(), spaceId);
        }
        
        return new SpaceDetailResponseDto(space, isBookmarked);
    }

    private boolean checkSpaceBookmark(Long memberId, String spaceId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. id=" + memberId));
        
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("공간이 존재하지 않습니다. id=" + spaceId));
        
        return spaceBookmarkRepository.findByMemberAndSpace(member, space).isPresent();
    }
}