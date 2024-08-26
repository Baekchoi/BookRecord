package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.CommentDto;
import com.example.bookrecord2.dto.ReviewDto;
import com.example.bookrecord2.entity.Book;
import com.example.bookrecord2.entity.Review;
import com.example.bookrecord2.entity.User;
import com.example.bookrecord2.exception.impl.UnauthorizedException;
import com.example.bookrecord2.repository.BookRepository;
import com.example.bookrecord2.repository.ReviewRepository;
import com.example.bookrecord2.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void addReview(ReviewDto reviewDto, String userNickname) {
        User user = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        Book book = bookRepository.findById(reviewDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("책을 찾을 수 없습니다."));

        Review review = Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .user(user)
                .book(book)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(review -> {
                    ReviewDto dto = ReviewDto.builder()
                            .bookId(review.getBook().getId())
                            .title(review.getTitle())
                            .userNickname(review.getUser().getNickname())
                            .createdAt(review.getCreatedAt())
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ReviewDto getReviewWithComments(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("감상문을 찾을 수 없습니다."));

        ReviewDto dto = ReviewDto.builder()
                .bookId(review.getBook().getId())
                .title(review.getTitle())
                .content(review.getContent())
                .userNickname(review.getUser().getNickname())
                .createdAt(review.getCreatedAt())
                .comments(review.getComments().stream()
                        .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                        .map(comment -> new CommentDto(
                                comment.getContent(),
                                comment.getCreatedAt(),
                                comment.getUser().getNickname()
                                ))
                        .collect(Collectors.toList())).build();
        return dto;
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewDto reviewDto, String userNickname) {
        if (reviewId == null) {
            throw new IllegalArgumentException("reviewId null");
        }
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("감상문을 찾을 수 없습니다."));

        if (!review.getUser().getNickname().equals(userNickname)) {
            throw new UnauthorizedException();
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
    }

    public void deleteReview(Long reviewId, String userNickname) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("감상문을 찾을 수 없습니다."));

        if (!review.getUser().getNickname().equals(userNickname)) {
            throw new UnauthorizedException();
        }

        reviewRepository.delete(review);
    }
}
