package com.mumu.mumu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//일단 다 전달하는 걸로 진행
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EduBookmarkResponseDTO {
    private Long eduId;
    private String field;
    private String eduName;
    private String eduContent;
    private String eduMethod;
    private String maxCapacity;
    private String recruitmentStartDate;
    private String recruitmentEndDate;
    private String recruitmentStartTime;
    private String recruitmentEndTime;
    private String eduLocationName;
    private String eduAddress;
    private Double eduLocationLongitude;
    private Double eduLocationLatitude;
    private String eduTarget;
    private String eduUrl;
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