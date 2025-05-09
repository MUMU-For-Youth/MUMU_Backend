package com.mumu.mumu.dto;

import com.mumu.mumu.domain.Edu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EduDetailResponseDto {

    private long eduId;
    private String eduName;
    private String eduImage;
    private String eduDate; // 일시(2025.04.22(화))
    private String field; // 분야
    private String eduMethod; // 방식
    private String eduAddress; // 주소
    private String eduSchedule; // 일정(12:00~18:00)
    private String eduTarget; // 대상
    private String eduLocationName;// 장소 이름
    private String max_capacity; // 모집정원
    private String eduTeacher; // 강사명
    private String eduUrl;
    private boolean bookmarked;


    // 생성자
    public EduDetailResponseDto(Edu edu, boolean isBookmarked) {
        this.eduId = edu.getEduId();
        this.eduName = edu.getEduName();
        this.eduImage = edu.getEduImage();
        this.eduDate = formatEduDate(edu.getRecruitmentStartDate(), edu.getRecruitmentEndDate());
        this.field = edu.getField();
        this.eduMethod = edu.getEduMethod();
        this.eduAddress = formatEduAddress(edu.getEduAddress());
        this.eduSchedule = formatEduSchedule(edu.getRecruitmentStartTime(), edu.getRecruitmentEndTime());
        this.eduTarget = edu.getEduTarget();
        this.eduLocationName = edu.getEduLocationName();
        this.max_capacity = edu.getMaxCapacity();
        this.eduTeacher = edu.getEduTeacher();
        this.eduUrl = edu.getEduUrl();
        this.bookmarked = isBookmarked;
    }

    // 날짜를 "2025.04.22(화)" 혹은 "2025.04.22(화)~2025.04.25(금)" 형식으로 반환
    private String formatEduDate(String startDate, String endDate) {
        if (startDate != null && !startDate.isBlank() && endDate != null && !endDate.isBlank()) {
            return formatDateWithDayOfWeek(startDate) + "~" + formatDateWithDayOfWeek(endDate);
        } else if (startDate != null && !startDate.isBlank()) {
            return formatDateWithDayOfWeek(startDate);
        } else if (endDate != null && !endDate.isBlank()) {
            return formatDateWithDayOfWeek(endDate);
        } else {
            return null;
        }
    }

    // 날짜 문자열에 요일 붙여서 반환
    private String formatDateWithDayOfWeek(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr); // ISO 형식: yyyy-MM-dd
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            String formattedDate = date.format(formatter);
            String dayOfWeek = getKoreanDayOfWeek(date);
            return formattedDate + "(" + dayOfWeek + ")";
        } catch (Exception e) {
            return dateStr; // 파싱 실패 시 원본 반환
        }
    }

    // 요일을 한글로 반환
    private String getKoreanDayOfWeek(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case MONDAY -> "월";
            case TUESDAY -> "화";
            case WEDNESDAY -> "수";
            case THURSDAY -> "목";
            case FRIDAY -> "금";
            case SATURDAY -> "토";
            case SUNDAY -> "일";
        };
    }

    // edu_start_time과 edu_end_time을 결합하여 edu_schedule을 "10:00~12:00" 형식으로 반환
    private String formatEduSchedule(String startTime, String endTime) {
        // 시간 값이 비어 있거나 형식이 잘못되었을 경우 처리
        if (startTime == null || startTime.isBlank() || endTime == null || endTime.isBlank()) {
            return ""; // 빈 문자열 반환
        }

        // 시간 값을 두 자릿수로 맞추어 형식 변경 (예: "14" -> "14:00")
        String formattedStartTime = formatTime(startTime);
        String formattedEndTime = formatTime(endTime);

        return formattedStartTime + "~" + formattedEndTime;
    }

    // 시간 값을 두 자릿수로 포맷팅
    private String formatTime(String time) {
        if (time.length() == 1) {
            return "0" + time + ":00"; // "14" -> "14:00"
        } else if (time.length() == 2) {
            return time + ":00"; // "14" -> "14:00"
        }
        return time; // 유효하지 않으면 그대로 반환
    }

    // 서울특별시 00구까지만 출력
    private String formatEduAddress(String address) {
        if (address != null && address.startsWith("서울특별시")) {
            String[] addressParts = address.split(" ");
            if (addressParts.length >= 2) {
                return addressParts[0] + " " + addressParts[1]; // "서울특별시 00구" 형태
            }
        }
        return address; // 서울특별시가 아닌 경우 원본 주소 반환
    }
}