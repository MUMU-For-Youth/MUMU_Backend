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
    private Long memberId;

    private String nickname;
    private String email;
    private String refreshToken;
    private String accessToken;
    private String ageRange;
    private String sex;
    private String kakaoId;
    private String naverId;
    private String googleId;
}
