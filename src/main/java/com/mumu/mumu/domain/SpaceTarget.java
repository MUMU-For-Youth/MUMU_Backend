package com.mumu.mumu.domain;

import java.util.Arrays;
import java.util.List;

public enum SpaceTarget {
    SEOUL_CITIZEN("서울시민", Arrays.asList("서울시", "구민", "구 주민")),
    NO_LIMIT("제한없음", Arrays.asList("제한없음", "성인, 어린이(9세미만), 유아")),
    TEENAGER("10대", Arrays.asList("만 9세~24세", "초등4학년~중등3학년 학생")),
    TWENTIES("20대", Arrays.asList("만 19세~만 45세", "만 9세~24세", "청년", "만 19세~만 39세", "성인")),
    THIRTIES("30대", Arrays.asList("만 19세~만 45세", "청년", "만 19세~만 39세", "성인")),
    FORTIES("40대", Arrays.asList("만 19세~만 45세", "청년", "만 19세~만 39세", "성인")),
    FEMALE("여성", Arrays.asList("여성")),
    SPECIAL("특이조건", Arrays.asList("예비군", "교육 이수자 혹은 사용 가능자(중학생 이하 보호자 동반 필수)", "구로구 내에서 활동하는 문화예술 공연단체나 개인"));

    private final String displayName;
    private final List<String> targetValues;

    SpaceTarget(String displayName, List<String> targetValues) {
        this.displayName = displayName;
        this.targetValues = targetValues;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getTargetValues() {
        return targetValues;
    }

    public static SpaceTarget fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(target -> target.displayName.equals(displayName))
                .findFirst()
                .orElse(null);
    }
} 