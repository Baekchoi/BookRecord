package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.CommentDto;
import com.example.bookrecord2.entity.Comment;
import com.example.bookrecord2.entity.Review;
import com.example.bookrecord2.entity.User;
import com.example.bookrecord2.exception.impl.UnauthorizedException;
import com.example.bookrecord2.repository.CommentRepository;
import com.example.bookrecord2.repository.ReviewRepository;
import com.example.bookrecord2.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public void addComment(CommentDto commentDto, Long reviewId, String userNickname){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("감상문을 찾을 수 없습니다."));
        User user = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .review(review)
                .user(user)
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
        review.getComments().add(comment);
    }

    @Transactional
    public void updateComment(Long reviewId, Long commentId, CommentDto commentDto, String userNickname){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("감상문을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        if(!comment.getReview().getId().equals(review.getId())){
            throw new RuntimeException("해당 감상문에 등록된 댓글이 아닙니다.");
        }

        if (!comment.getUser().getNickname().equals(userNickname)){
            throw new UnauthorizedException();
        }

        comment.setContent(commentDto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
    }

    public void deleteComment(Long reviewId, Long commentId, String userNickname){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("감상문을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
        if (!comment.getReview().getId().equals(review.getId())){
            throw new RuntimeException("해당 감상문에 등록된 댓글이 아닙니다.");
        }

        if (!comment.getUser().getNickname().equals(userNickname)){
            throw new UnauthorizedException();
        }

        commentRepository.delete(comment);
    }
}
