package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDTO {
    private Long reviewNo;
    private Long movieNo;
    private String memberId;
    private Integer score;
    private String reviewContent;
    private LocalDate regDate;
    private LocalDate updateDate;

    private int profileImgNo;
    private String fileName;
    private String fileRename;
    private String filePath;
    private MultipartFile uploadFile;
}
