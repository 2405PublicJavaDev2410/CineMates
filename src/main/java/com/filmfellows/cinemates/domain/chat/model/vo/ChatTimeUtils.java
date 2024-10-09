package com.filmfellows.cinemates.domain.chat.model.vo;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class ChatTimeUtils {
    public static String getRelativeTime(Timestamp createdTime) {
        // Timestamp를 LocalDateTime으로 변환
        LocalDateTime localDateTime = createdTime.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, localDateTime);

        if (Math.abs(duration.toMinutes()) < 10) {
            return "방금";
        } else if (Math.abs(duration.toHours()) < 1) {
            return Math.abs(duration.toMinutes()) + "분 전";
        } else if (Math.abs(duration.toDays()) < 1) {
            return Math.abs(duration.toHours()) + "시간 전";
        } else if (Math.abs(duration.toDays()) < 7) {
            return Math.abs(duration.toDays()) + "일 전";
        } else {
            return localDateTime.toLocalDate().toString(); // 날짜 포맷으로 리턴
        }
    }
}
