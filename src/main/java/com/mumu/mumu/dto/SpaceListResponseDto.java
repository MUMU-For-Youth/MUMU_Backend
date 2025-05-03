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

    private String spaceId;
    private String spaceType;
    private String spaceImage;
    private String spaceName;
    private String spaceTime;
    private String spaceTarget;
    private String spaceLocation;
    private String contactNumber;
    private String spaceUrl;

    private boolean bookmarked;

    // 생성자
    public SpaceListResponseDto(Space space) {
        this.spaceId = space.getSpaceId();
        this.spaceType = space.getType();
        this.spaceImage = space.getSpaceImage();
        this.spaceName = space.getSpaceName();
        this.spaceTime = formatTime(space.getOpeningTime(), space.getClosingTime());
        this.spaceTarget = space.getSpaceTarget();
        this.spaceLocation = formatSpaceAddress(space.getSpaceLocation()); // 서울특별시 00구까지만 표시
        this.contactNumber = space.getContactNumber();
        this.spaceUrl = space.getSpaceUrl();
        this.bookmarked = false;
    }

    // 북마크 상태를 포함한 생성자
    public SpaceListResponseDto(Space space, boolean isBookmarked) {
        this.spaceId = space.getSpaceId();
        this.spaceType = space.getType();
        this.spaceImage = space.getSpaceImage();
        this.spaceName = space.getSpaceName();
        this.spaceTime = formatTime(space.getOpeningTime(), space.getClosingTime());
        this.spaceTarget = space.getSpaceTarget();
        this.spaceLocation = formatSpaceAddress(space.getSpaceLocation()); // 서울특별시 00구까지만 표시
        this.contactNumber = space.getContactNumber();
        this.spaceUrl = space.getSpaceUrl();
        this.bookmarked = isBookmarked;
    }

    // 시간을 10:00~17:00" 형식으로 변환
    private String formatTime(LocalTime openingTime, LocalTime closingTime) {
        if (openingTime == null || closingTime == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedOpeningTime = openingTime.format(formatter);
        String formattedClosingTime = closingTime.format(formatter);

        return formattedOpeningTime + "~" + formattedClosingTime;
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