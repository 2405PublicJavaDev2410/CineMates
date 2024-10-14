package com.filmfellows.cinemates.app.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RelativeTime {
    private Timestamp roomDate;
    private String relativeTime;
}
