package com.example.bookrecord2.dto;

import lombok.Getter;

@Getter
public class BookSummary {

    private String title;
    private String author;
    private String publisher;

    public BookSummary(String title, String author, String publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }
}
