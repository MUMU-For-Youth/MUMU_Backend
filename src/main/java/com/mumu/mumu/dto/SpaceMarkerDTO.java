package com.mumu.mumu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpaceMarkerDTO {
    private String spaceId;
    private String spaceName;
    private String spaceLocation;
    private Double spaceLocationLongitude;
    private Double spaceLocationLatitude;
    private String spaceImage;
}
