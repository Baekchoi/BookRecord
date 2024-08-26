package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.UserLibraryDto;
import com.example.bookrecord2.dto.constant.ReadingStatus;
import com.example.bookrecord2.entity.Book;
import com.example.bookrecord2.entity.User;
import com.example.bookrecord2.entity.UserLibrary;
import com.example.bookrecord2.repository.BookRepository;
import com.example.bookrecord2.repository.UserLibraryRepository;
import com.example.bookrecord2.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // 읽고 싶은 책 등록
    @Transactional
    public void addWantToRead(UserLibraryDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("책을 찾을 수 없습니다."));

        UserLibrary userLibrary = UserLibrary.builder()
                                                .user(user)
                                                .book(book)
                                                .status(ReadingStatus.WANT_TO_READ)
                                                .build();

        userLibraryRepository.save(userLibrary);
    }

    // 읽고 있는 책 등록
    @Transactional
    public void addReading(UserLibraryDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("책을 찾을 수 없습니다."));

        UserLibrary userLibrary = UserLibrary.builder()
                                                .user(user)
                                                .book(book)
                                                .status(ReadingStatus.READING)
                                                .startDate(dto.getStartDate())
                                                .progress(dto.getProgress())
                                                .build();

        userLibraryRepository.save(userLibrary);
    }

    // 읽은 책 등록
    @Transactional
    public void addCompleted(UserLibraryDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("책을 찾을 수 없습니다."));

        UserLibrary userLibrary = UserLibrary.builder()
                                                .user(user)
                                                .book(book)
                                                .status(ReadingStatus.COMPLETED)
                                                .startDate(dto.getStartDate())
                                                .endDate(dto.getEndDate())
                                                .rating(dto.getRating())
                                                .review(dto.getReview())
                                                .build();

        userLibraryRepository.save(userLibrary);
    }

    // 서재에서 책 상태 수정
    @Transactional
    public void updateEntry(Long entryId, UserLibraryDto dto) {
        UserLibrary entry = userLibraryRepository.findById(entryId)
                .orElseThrow(() -> new EntityNotFoundException("서재에 등록된 책을 찾을 수 없습니다."));

        entry.setStatus(dto.getStatus());

        if (dto.getStatus() == ReadingStatus.READING) {
            entry.setStartDate(dto.getStartDate());
            entry.setProgress(dto.getProgress());
        } else if (dto.getStatus() == ReadingStatus.COMPLETED) {
            entry.setStartDate(dto.getStartDate());
            entry.setEndDate(dto.getEndDate());
            entry.setRating(dto.getRating());
            entry.setReview(dto.getReview());
        }
    }

    // 서재에서 상태별로 목록 조회
    @Transactional(readOnly = true)
    public List<UserLibraryDto> getEntriesByStatus(ReadingStatus status, Long userId) {
        List<UserLibrary> entries = userLibraryRepository.findByUserIdAndStatus(userId, status);

        return entries.stream()
                .map(entry -> {
                    UserLibraryDto.UserLibraryDtoBuilder dtoBuilder = UserLibraryDto.builder()
                            .title(entry.getBook().getTitle());

                    if (status == ReadingStatus.READING) {
                        dtoBuilder.startDate(entry.getStartDate())
                                  .progress(entry.getProgress());
                    } else if (status == ReadingStatus.COMPLETED) {
                        dtoBuilder.startDate(entry.getEndDate())
                                  .endDate(entry.getStartDate())
                                  .rating(entry.getRating());
                    }
                    return dtoBuilder.build();
                })
                .collect(Collectors.toList());
    }

    // 특정 항목 조회
    @Transactional(readOnly = true)
    public UserLibraryDto getEntryDetailById(Long id) {
        UserLibrary entry = userLibraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("서재에 등록된 책을 찾을 수 없습니다."));

        return UserLibraryDto.builder()
                .bookId(entry.getBook().getId())
                .userId(entry.getUser().getId())
                .title(entry.getBook().getTitle())
                .author(entry.getBook().getAuthor())
                .publisher(entry.getBook().getPublisher())
                .isbn(entry.getBook().getIsbn())
                .totalPage(entry.getBook().getTotalPage())
                .barcode(entry.getBook().getBarcode())
                .status(entry.getStatus())
                .startDate(entry.getStartDate())
                .endDate(entry.getEndDate())
                .progress(entry.getProgress())
                .rating(entry.getRating())
                .review(entry.getReview())
                .build();
    }

    // 책 삭제
    @Transactional
    public void deleteEntry(Long id) {
        UserLibrary userLibrary = userLibraryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("서재에 등록된 책을 찾을 수 없습니다."));
        userLibraryRepository.delete(userLibrary);
    }

}
