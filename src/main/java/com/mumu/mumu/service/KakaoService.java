package com.mumu.mumu.service;

import com.mumu.mumu.domain.Member;
import com.mumu.mumu.dto.KakaoTokenResponseDto;
import com.mumu.mumu.dto.KakaoUserInfoResponseDto;
import com.mumu.mumu.repository.MemberRepository;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private String clientId;
    private final String KAUTH_TOKEN_URL_HOST;
    private final String KAUTH_USER_URL_HOST;
    @Autowired
    private MemberRepository memberRepository;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @Autowired
    public KakaoService(@Value("${kakao.client_id}") String clientId) {
        this.clientId = clientId;
        KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
    }


    // 카카오로부터 액세스 토큰을 얻는 메서드
    public KakaoTokenResponseDto getTokenFromKakao(String code) {
        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

        // 요청 본문 설정 (x-www-form-urlencoded 형식)
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("redirect_uri", encodedRedirectUri);
        formData.add("code", code);

        return WebClient.create(KAUTH_TOKEN_URL_HOST)
                .post()
                .uri("/oauth/token")
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(formData)) // <-- body로 보냄
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                            System.err.println("❗️4xx 오류 발생 - 응답 본문: " + errorBody);
                            return Mono.error(new RuntimeException("카카오 요청 실패: " + errorBody));
                        })
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Internal Server Error"))
                )
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();
    }

//    public KakaoTokenResponseDto getTokenFromKakao(String code) {
//        String redirectUri = "http://localhost:3000/MUMU_Frontend";
//        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
//                .uri(uriBuilder -> uriBuilder
//                        .scheme("https")
//                        .path("/oauth/token")
//                        .queryParam("grant_type", "authorization_code")
//                        .queryParam("client_id", clientId)
//                        .queryParam("redirect_uri", redirectUri)
//                        .queryParam("code", code)
//                        .build(true))
//                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
//                .retrieve()
//                // 예외 처리
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
//                        clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
//                            System.err.println("❗️4xx 오류 발생 - 응답 본문: " + errorBody);
//                            return Mono.error(new RuntimeException("카카오 요청 실패: " + errorBody));
//                        })
//                )
//                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
//                .bodyToMono(KakaoTokenResponseDto.class)
//                .block();
//
//        log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
//        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
//        log.info(" [Kakao Service] Id Token ------> {}", kakaoTokenResponseDto.getIdToken());
//        log.info(" [Kakao Service] Scope ------> {}", kakaoTokenResponseDto.getScope());
//
//        return kakaoTokenResponseDto;
//    }

    // 액세스 토큰을 이용해 카카오 사용자 정보를 얻는 메서드
    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                // 예외 처리
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());

        return userInfo;
    }

    // 회원 정보를 저장 또는 갱신하는 메서드
    public void saveOrUpdateMember(String accessToken, String refreshToken) {
        KakaoUserInfoResponseDto userInfo = getUserInfo(accessToken);
        String kakaoId = userInfo.getId().toString();
        String email = userInfo.getKakaoAccount().getEmail();  // null일 수도 있음

        Optional<Member> existing = memberRepository.findByKakaoId(kakaoId);

        Member member = existing.orElse(Member.builder()
                .kakaoId(kakaoId)
                .email(email)
                .build());

        member.setAccessToken(accessToken);
        member.setRefreshToken(refreshToken);

        memberRepository.save(member);
    }

    // 리프레시 토큰을 이용하여 액세스 토큰을 갱신하는 메서드
    public String refreshAccessToken(String refreshToken) {
        // 리프레시 토큰을 사용해 새로운 액세스 토큰을 얻는 API 요청 로직
        KakaoTokenResponseDto tokenResponse = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "refresh_token")
                        .queryParam("client_id", clientId)
                        .queryParam("refresh_token", refreshToken)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Token")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        // 새로 받은 액세스 토큰만 반환
        return tokenResponse.getAccessToken();
    }

    // 로그아웃
    public void kakaoLogout(String accessToken) {
        WebClient.create(KAUTH_USER_URL_HOST)
                .post()
                .uri("/v1/user/logout")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    return Mono.error(new RuntimeException("클라이언트 오류 - 카카오 로그아웃 실패"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    return Mono.error(new RuntimeException("서버 오류 - 카카오 로그아웃 실패"));
                })
                .bodyToMono(String.class)
                .block(); // 동기 처리
    }
}