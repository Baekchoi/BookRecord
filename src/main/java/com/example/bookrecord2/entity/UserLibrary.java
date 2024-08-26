package com.example.bookrecord2.entity;

import com.example.bookrecord2.dto.constant.ReadingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "USER LIBRARY")
public class UserLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private ReadingStatus status;

    private LocalDate startDate;
    private LocalDate endDate;
    private int progress;
    private int rating;
    private String review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
