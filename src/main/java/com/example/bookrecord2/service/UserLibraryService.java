package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.UserLibraryDto;
import com.example.bookrecord2.dto.constant.ReadingStatus;
import com.example.bookrecord2.entity.UserLibrary;
import com.example.bookrecord2.repository.BookRepository;
import com.example.bookrecord2.repository.UserLibraryRepository;
import com.example.bookrecord2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // 읽고 싶은 책 등록
    public void addWantToRead(UserLibraryDto dto) {
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setBook(bookRepository.findById(dto.getBookId()).orElseThrow());
        userLibrary.setUser(userRepository.findById(dto.getUserId()).orElseThrow());
        userLibrary.setStatus(ReadingStatus.WANT_TO_READ);
        userLibraryRepository.save(userLibrary);
    }

    // 읽고 있는 책 등록
    public void addReading(UserLibraryDto dto) {
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setBook(bookRepository.findById(dto.getBookId()).orElseThrow());
        userLibrary.setUser(userRepository.findById(dto.getUserId()).orElseThrow());
        userLibrary.setStatus(ReadingStatus.READING);
        userLibrary.setStartDate(dto.getStartDate());
        userLibrary.setProgress(dto.getProgress());
        userLibraryRepository.save(userLibrary);
    }

    // 읽은 책 등록
    public void addCompleted(UserLibraryDto dto) {
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setBook(bookRepository.findById(dto.getBookId()).orElseThrow());
        userLibrary.setUser(userRepository.findById(dto.getUserId()).orElseThrow());
        userLibrary.setStatus(ReadingStatus.COMPLETED);
        userLibrary.setStartDate(dto.getStartDate());
        userLibrary.setEndDate(dto.getEndDate());
        userLibrary.setRating(dto.getRating());
        userLibrary.setReview(dto.getReview());
        userLibraryRepository.save(userLibrary);
    }
}
