package com.filmfellows.cinemates.domain.reservation.model.vo;

public class LocationUtil {
    public static String getRegionName(int code) {
        switch (code) {
            case 1: return "서울";
            case 2: return "인천";
            case 3: return "경기";
            case 4: return "충청/대전/세종";
            case 5: return "부산/울산";
            case 6: return "경상북도/대구";
            case 7: return "강원";
            case 8: return "전라/광주/전북/제주";
            default: return "기타";
        }
    }
}
