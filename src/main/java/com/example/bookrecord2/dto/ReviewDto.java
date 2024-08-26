package com.example.bookrecord2.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewDto {

    private Long bookId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String userNickname;
    private List<CommentDto> comments;

}
