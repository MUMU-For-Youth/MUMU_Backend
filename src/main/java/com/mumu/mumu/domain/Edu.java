package com.mumu.mumu.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "edu")
public class Edu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduId;

    private String field;
    private String eduName;

    @Column(columnDefinition = "TEXT")
    private String eduContent;

    private String eduMethod;
    private String maxCapacity;
    private String recruitmentStartDate;
    private String recruitmentEndDate;
    private String recruitmentStartTime;
    private String recruitmentEndTime;
    private String eduLocationName;
    private String eduAddress;

    @Column(precision = 10, scale = 6)
    private Double eduLocationLongitude;

    @Column(precision = 10, scale = 6)
    private Double eduLocationLatitude;

    private String eduTarget;

    @Column(length = 500)
    private String eduUrl;

    @Column(length = 500)
    private String eduImage;

    private String eduFee;
    private String recruitmentStatus;
    private String eduTeacher;
    private String eduStartDate;
    private String eduEndDate;
    private String eduStartTime;
    private String eduEndTime;
    private String eduRegion;
}

