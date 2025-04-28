package com.mumu.mumu.controller;

import com.mumu.mumu.domain.Member;
import com.mumu.mumu.dto.EduBookmarkDTO;
import com.mumu.mumu.dto.SpaceBookmarkDTO;
import com.mumu.mumu.service.BookmarkService;
import com.mumu.mumu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /** 교육 북마크 조회(미완성) */
    @GetMapping("/edu/bookmark")
    public ResponseEntity<Boolean> checkEduBookmark(
            @RequestParam("access_token") String accessToken,
            @RequestParam(value="edu_id", required = false) String eduId
    ) {
        Long memberId = getMemberIdFromAccessToken(accessToken);

        boolean isBookmarked = bookmarkService.checkEduBookmark(memberId, eduId);
        return ResponseEntity.ok(isBookmarked);
    }

    /** 교육 북마크 ON/OFF 토글 */
    @PostMapping("/edu/bookmark")
    public ResponseEntity<Void> toggleEduBookmark(
            @RequestParam("access_token") String accessToken,
            @RequestBody EduBookmarkDTO eduBookmarkDTO
    ) {
        Long memberId = getMemberIdFromAccessToken(accessToken);
        bookmarkService.toggleEduBookmark(memberId, eduBookmarkDTO);
        return ResponseEntity.ok().build();
    }

    /** 공간 북마크 조회(미완성) */
    @GetMapping("/space/bookmark")
    public ResponseEntity<Boolean> checkSpaceBookmark(
            @RequestParam("access_token") String accessToken,
            @RequestParam(value="space_id", required = false) String spaceId
    ) {
        Long memberId = getMemberIdFromAccessToken(accessToken);
        boolean isBookmarked = bookmarkService.checkSpaceBookmark(memberId, spaceId);
        return ResponseEntity.ok(isBookmarked);
    }

    /** 공간 북마크 ON/OFF 토글 */
    @PostMapping("/space/bookmark")
    public ResponseEntity<Void> toggleSpaceBookmark(
            @RequestParam("access_token") String accessToken,
            @RequestBody SpaceBookmarkDTO spaceBookmarkDTO
    ) {
        Long memberId = getMemberIdFromAccessToken(accessToken);
        bookmarkService.toggleSpaceBookmark(memberId, spaceBookmarkDTO);
        return ResponseEntity.ok().build();
    }


    /** ✨ access_token으로 member_id를 찾는 임시 메서드 */
    private final MemberRepository memberRepository;
    private Long getMemberIdFromAccessToken(String accessToken) {
        Member member = memberRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 access_token입니다."));
        return member.getMemberId();
    }
}
