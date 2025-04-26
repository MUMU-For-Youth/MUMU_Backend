package com.mumu.mumu.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "age_range")
    private String ageRange;

    @Column(name = "sex")
    private String sex;

    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "naver_id")
    private String naverId;

    @Column(name = "google_id")
    private String googleId;
}