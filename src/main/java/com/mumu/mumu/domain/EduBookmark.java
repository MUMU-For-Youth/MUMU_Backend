package com.mumu.mumu.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "edu_bookmark")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EduBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_bookmark_id")
    private Long eduBookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_id", nullable = false)
    private Edu edu;
}