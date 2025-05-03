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
    @Column(name = "space_id")
    private String spaceId;

    @Column(name = "space_name")
    private String spaceName;

    @Column(name = "space_location")
    private String spaceLocation;

    @Column(name = "space_location_longitude")
    private Double spaceLocationLongitude;

    @Column(name = "space_location_latitude")
    private Double spaceLocationLatitude;

    @Column(name = "space_type")
    private String type;

    @Column(name = "space_target")
    private String spaceTarget;

    @Column(name = "space_url", columnDefinition = "TEXT")
    private String spaceUrl;

    @Column(name = "space_image", columnDefinition = "TEXT")
    private String spaceImage;

    @Column(name = "region")
    private String region;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @Column(name = "service_start_date")
    private LocalDate serviceStartDate;

    @Column(name = "service_end_date")
    private LocalDate serviceEndDate;

    @Column(name = "rsv_start_date")
    private LocalDate rsvStartDate;

    @Column(name = "rsv_end_date")
    private LocalDate rsvEndDate;

    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    @Column(name = "space_fee")
    private String spaceFee;

    @Column(name = "space_status")
    private String spaceStatus;
}