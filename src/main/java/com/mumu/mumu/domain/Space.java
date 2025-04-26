package com.mumu.mumu.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "space")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String spaceId;
    private String spaceName;
    private String spaceLocation;

    @Column(precision = 10, scale = 6)
    private Double spaceLocationLongitude;

    @Column(precision = 10, scale = 6)
    private Double spaceLocationLatitude;

    private String spaceType;
    private String spaceTarget;

    @Column(columnDefinition = "TEXT")
    private String spaceUrl;

    @Column(columnDefinition = "TEXT")
    private String spaceImage;

    private String region;
    private String contactNumber;

    private LocalTime openingTime;
    private LocalTime closingTime;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private LocalDate rsvStartDate;
    private LocalDate rsvEndDate;

    @Column(columnDefinition = "TEXT")
    private String detail;

    private String spaceFee;
    private String spaceStatus;
}
