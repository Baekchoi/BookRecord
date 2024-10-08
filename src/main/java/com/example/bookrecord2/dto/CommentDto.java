package com.example.bookrecord2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {

    private String content;
    private LocalDateTime createdAt;
    private String userNickname;

}
