package com.filmfellows.cinemates.domain.chat.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Chat {
    private Integer chatNo;
    private String chatContent;
    private Integer roomNo;
    private String memberId;
    private Timestamp chatDate;
    private String reportStatus;
}
