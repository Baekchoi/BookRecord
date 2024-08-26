package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.ReviewDto;
import com.example.bookrecord2.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> addReview(@RequestBody ReviewDto reviewDto/*, Principal principal*/) {
        String userNickname = "doy";
        reviewService.addReview(reviewDto, userNickname /*principal.getName()*/);
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
    public ResponseEntity<String> updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto/*, Principal principal*/) {
        String userNickname = "doy";
        reviewService.updateReview(id, reviewDto, userNickname);
        return ResponseEntity.ok("감상문이 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id /*,Principal principal*/) {
        String userNickname = "doy";
        reviewService.deleteReview(id, userNickname);
        return ResponseEntity.ok("감상문이 삭제되었습니다.");
    }
}
