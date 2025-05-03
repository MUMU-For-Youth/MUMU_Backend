package com.mumu.mumu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpaceBookmarkResponseDTO {
    private String spaceId;
    private String spaceName;
    private String spaceLocation;
    private Double spaceLocationLongitude;
    private Double spaceLocationLatitude;
    private String type;
    private String spaceTarget;
    private String spaceUrl;
    private String spaceImage;
    private String region;
    private String contactNumber;
    private String openingTime;
    private String closingTime;
    private String serviceStartDate;
    private String serviceEndDate;
    private String rsvStartDate;
    private String rsvEndDate;
    private String detail;
    private String spaceFee;
    private String spaceStatus;
}