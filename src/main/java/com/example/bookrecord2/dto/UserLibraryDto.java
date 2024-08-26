package com.example.bookrecord2.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserLibraryDto {
    private Long bookId;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int progress;
    private int rating;
    private String review;
}
