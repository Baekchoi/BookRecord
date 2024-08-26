package com.example.bookrecord2.dto;

import com.example.bookrecord2.dto.constant.ReadingStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLibraryDto {
    private Long bookId;
    private Long userId;

    private String title;
    private String author;
    private String publisher;
    private String isbn;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int totalPage;
    private String barcode;

    private ReadingStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int progress;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int rating;
    private String review;
}
