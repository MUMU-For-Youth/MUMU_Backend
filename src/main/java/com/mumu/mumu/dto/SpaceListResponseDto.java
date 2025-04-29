package com.mumu.mumu.dto;

import com.mumu.mumu.domain.Space;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpaceListResponseDto {

    private String spaceType;
    private String spaceName;
    private String openingTime; // 월~금 이용시간
    private String closingTime; // 월~금 이용시간
    private String spaceTarget;
    private String spaceLocation;
    private String contactNumber;
    private String spaceUrl;

    private boolean bookmarked;

    // 생성자
    public SpaceListResponseDto(Space space) {
        this.spaceType = space.getSpaceType();
        this.spaceName = space.getSpaceName();
        this.openingTime = formatTime(space.getOpeningTime());
        this.closingTime = formatTime(space.getClosingTime());
        this.spaceTarget = space.getSpaceTarget();
        this.spaceLocation = formatSpaceAddress(space.getSpaceLocation()); // 서울특별시 00구까지만 표시
        this.contactNumber = space.getContactNumber();
        this.spaceUrl = space.getSpaceUrl();
        this.bookmarked = false;
    }

    // 시간을 "월~금, 10:00~17:00" 형식으로 변환
    private String formatTime(LocalTime time) {
        if (time == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // 24시간 형식으로 포맷
        String formattedTime = time.format(formatter);

        // "월~금, 10:00~17:00" 형식으로 리턴
        return "월~금, " + formattedTime + "~" + formattedTime;
    }

    // 서울특별시 00구까지만 출력
    private String formatSpaceAddress(String address) {
        if (address != null && address.startsWith("서울특별시")) {
            String[] addressParts = address.split(" ");
            if (addressParts.length >= 2) {
                return addressParts[0] + " " + addressParts[1]; // "서울특별시 00구" 형태
            }
        }
        return address; // 서울특별시가 아닌 경우 원본 주소 반환
    }
}