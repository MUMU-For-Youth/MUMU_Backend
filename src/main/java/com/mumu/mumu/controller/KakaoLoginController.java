package com.mumu.mumu.controller;

import com.mumu.mumu.dto.KakaoTokenResponseDto;
import com.mumu.mumu.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @GetMapping("/auth/kakao/url")
    public ResponseEntity<?> getKakaoLoginUrl() {
        String url = "https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;

        return ResponseEntity.ok(Map.of("url", url));
    }

    @GetMapping("/auth/kakao/redirect")
    public ResponseEntity<?> redirectToFrontend(@RequestParam("code") String code) {
        // 프론트에 인가 코드를 전달하는 리디렉션
        String frontendUrl = "https://mumu-for-youth.github.io/MUMU_Frontend/#/kakao/callback?code=" + code;
//        String frontendUrl = "http://localhost:3000/#/kakao/callback?code=" + code;
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", frontendUrl)
                .build();
    }

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
        // 1. 인가코드로 토큰 요청
        KakaoTokenResponseDto tokenDto = kakaoService.getTokenFromKakao(code);

        // 2. 사용자 정보 저장/업데이트
        kakaoService.saveOrUpdateMember(tokenDto.getAccessToken(), tokenDto.getRefreshToken());

        // 3. accessToken 등을 응답
        return ResponseEntity.ok(Map.of("accessToken", tokenDto.getAccessToken()));
    }

    @PostMapping("/auth/kakao/logout")
    public ResponseEntity<?> kakaoLogout(@RequestHeader("Authorization") String authHeader) {
        String accessToken = authHeader.replace("Bearer ", "");
        kakaoService.kakaoLogout(accessToken);
        return ResponseEntity.ok("카카오 로그아웃 완료");
    }
}
