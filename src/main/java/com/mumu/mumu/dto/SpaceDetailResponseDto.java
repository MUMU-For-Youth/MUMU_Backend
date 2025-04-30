package com.mumu.mumu.dto;

import com.mumu.mumu.domain.Space;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpaceDetailResponseDto {

    private String spaceId;
    private String spaceName;
    private String spaceImage;
    private String spaceTime; // 이용시간
    private String spaceTarget; // 대상자
    private String spaceLocation; // 주소
    private String contactNumber; // 전화번호
    private String spaceContent; // 상세설명
    private String serviceSchedule; // 장소사용 일정
    private String reservationSchedule; // 얘약 일정
    private String spaceUrl;
    private boolean bookmarked;

    // 생성자
    public SpaceDetailResponseDto(Space space, boolean isBookmarked) {
        this.spaceId = space.getSpaceId();
        this.spaceName = space.getSpaceName();
        this.spaceImage = space.getSpaceImage();
        this.spaceTime = formatTime(space.getOpeningTime(), space.getClosingTime());
        this.spaceTarget = space.getSpaceTarget();
        this.spaceLocation = formatSpaceAddress(space.getSpaceLocation()); // 서울특별시 00구까지만 표시
        this.contactNumber = space.getContactNumber();
        this.spaceContent = space.getDetail();
        this.serviceSchedule = formatDateRange(space.getServiceStartDate(), space.getServiceEndDate());
        this.reservationSchedule = formatDateRange(space.getRsvStartDate(), space.getRsvEndDate());
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

    private String formatDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String startDay = startDate.getDayOfWeek().getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.KOREAN);
        String endDay = endDate.getDayOfWeek().getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.KOREAN);

        return startDate.format(formatter) + "(" + startDay + ")~" +
                endDate.format(formatter) + "(" + endDay + ")";
    }
}