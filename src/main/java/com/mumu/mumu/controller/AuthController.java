package com.mumu.mumu.controller;

import com.mumu.mumu.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private KakaoService kakaoService;

    // 리프레시 토큰을 받아서 새로운 액세스 토큰을 발급
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is missing"));
        }

        try {
            // 리프레시 토큰을 통해 새로운 액세스 토큰 발급
            String newAccessToken = kakaoService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (Exception e) {
            // 예외 발생 시, 잘못된 리프레시 토큰 처리
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Invalid refresh token"));
        }
    }
}