package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.CommentDto;
import com.example.bookrecord2.dto.ReviewDto;
import com.example.bookrecord2.entity.Book;
import com.example.bookrecord2.entity.Review;
import com.example.bookrecord2.entity.User;
import com.example.bookrecord2.repository.BookRepository;
import com.example.bookrecord2.repository.ReviewRepository;
import com.example.bookrecord2.repository.UserRepository;
import lombok.AllArgsConstructor;
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
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Book book = bookRepository.findById(reviewDto.getBookId())
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));

        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setUser(user);
        review.setBook(book);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(review -> {
                    ReviewDto dto = new ReviewDto();
                    dto.setBookId(review.getBook().getId());
                    dto.setTitle(review.getTitle());
                    dto.setUserNickname(review.getUser().getNickname());
                    dto.setCreatedAt(review.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ReviewDto getReviewWithComments(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("감상문을 찾을 수 없습니다."));

        ReviewDto dto = new ReviewDto();
        dto.setBookId(review.getBook().getId());
        dto.setTitle(review.getTitle());
        dto.setContent(review.getContent());
        dto.setUserNickname(review.getUser().getNickname());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setComments(review.getComments().stream()
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .map(comment -> new CommentDto(
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUser().getNickname()
                        ))
                .collect(Collectors.toList()));
        return dto;
    }

    public void updateReview(Long reviewId, ReviewDto reviewDto, String userNickname) {
        System.out.println("reviewId: " + reviewId);
        if (reviewId == null) {
            throw new IllegalArgumentException("reviewId null");
        }
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("감상문을 찾을 수 없습니다."));

        if (!review.getUser().getNickname().equals(userNickname)) {
            throw new RuntimeException("본인이 작성한 감상문만 수정할 수 있습니다.");
        }

//        System.out.println("BookId:" + reviewDto.getBookId());
//        Book book = bookRepository.findById(reviewDto.getBookId())
//                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
//        review.setBook(book);

        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId, String userNickname) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("감상문을 찾을 수 없습니다."));

        if (!review.getUser().getNickname().equals(userNickname)) {
            throw new RuntimeException("본인이 작성한 감상문만 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);
    }
}
