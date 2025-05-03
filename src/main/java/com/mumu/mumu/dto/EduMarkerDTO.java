package com.mumu.mumu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EduMarkerDTO {
    private Long eduId;
    private String eduName;
    private String eduLocationName;
    private String eduAddress;
    private Double eduLocationLongitude;
    private Double eduLocationLatitude;
    private String eduImage;
}
