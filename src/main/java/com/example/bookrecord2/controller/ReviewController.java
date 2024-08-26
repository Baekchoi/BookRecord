package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.ReviewDto;
import com.example.bookrecord2.service.ReviewService;
import com.example.bookrecord2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> addReview(@RequestBody ReviewDto reviewDto, Principal principal) {
        String email = principal.getName();
        String nickname = userService.getNicknameByEmail(email);
        reviewService.addReview(reviewDto, nickname);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long id) {
        ReviewDto reviewDto = reviewService.getReviewWithComments(id);
        return ResponseEntity.ok(reviewDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto, Principal principal) {
        String email = principal.getName();
        String nickname = userService.getNicknameByEmail(email);
        reviewService.updateReview(id, reviewDto, nickname);
        return ResponseEntity.ok("감상문이 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id ,Principal principal) {
        String email = principal.getName();
        String nickname = userService.getNicknameByEmail(email);
        reviewService.deleteReview(id, nickname);
        return ResponseEntity.ok("감상문이 삭제되었습니다.");
    }
}
