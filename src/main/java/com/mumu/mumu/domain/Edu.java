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
    @Column(name = "edu_id") // DB 컬럼명과 맞게 매핑
    private Long eduId;

    @Column(name = "field")
    private String field;

    @Column(name = "edu_name")
    private String eduName;

    @Column(name = "edu_content", columnDefinition = "TEXT")
    private String eduContent;

    @Column(name = "edu_method")
    private String eduMethod;

    @Column(name = "max_capacity")
    private String maxCapacity;

    @Column(name = "recruitment_start_date")
    private String recruitmentStartDate;

    @Column(name = "recruitment_end_date")
    private String recruitmentEndDate;

    @Column(name = "recruitment_start_time")
    private String recruitmentStartTime;

    @Column(name = "recruitment_end_time")
    private String recruitmentEndTime;

    @Column(name = "edu_location_name")
    private String eduLocationName;

    @Column(name = "edu_address")
    private String eduAddress;

    @Column(name = "edu_location_longitude")
    private Double eduLocationLongitude;

    @Column(name = "edu_location_latitude")
    private Double eduLocationLatitude;

    @Column(name = "edu_target")
    private String eduTarget;

    @Column(name = "edu_url", length = 500)
    private String eduUrl;

    @Column(name = "edu_image", length = 500)
    private String eduImage;

    @Column(name = "edu_fee")
    private String eduFee;

    @Column(name = "recruitment_status")
    private String recruitmentStatus;

    @Column(name = "edu_teacher")
    private String eduTeacher;

    @Column(name = "edu_start_date")
    private String eduStartDate;

    @Column(name = "edu_end_date")
    private String eduEndDate;

    @Column(name = "edu_start_time")
    private String eduStartTime;

    @Column(name = "edu_end_time")
    private String eduEndTime;

    @Column(name = "edu_region")
    private String eduRegion;
}