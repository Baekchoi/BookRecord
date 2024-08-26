package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.CommentDto;
import com.example.bookrecord2.entity.Comment;
import com.example.bookrecord2.entity.Review;
import com.example.bookrecord2.entity.User;
import com.example.bookrecord2.repository.CommentRepository;
import com.example.bookrecord2.repository.ReviewRepository;
import com.example.bookrecord2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public void addComment(CommentDto commentDto, Long reveiwId, String userNickname){
        Review review = reviewRepository.findById(reveiwId)
                .orElseThrow(() -> new RuntimeException("감상문을 찾을 수 없습니다."));
        User user = userRepository.findByNickname(userNickname)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setReview(review);
        comment.setUser(user);
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
        review.getComments().add(comment);
    }

    public void updateComment(Long reviewId, Long commentId, CommentDto commentDto, String userNickname){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("감상문을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if(!comment.getReview().getId().equals(review.getId())){
            throw new RuntimeException("해당 감상문에 등록된 댓글이 아닙니다.");
        }

        if (!comment.getUser().getNickname().equals(userNickname)){
            throw new RuntimeException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(commentDto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public void deleteComment(Long reviewId, Long commentId, String userNickname){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("감상문을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!comment.getReview().getId().equals(review.getId())){
            throw new RuntimeException("해당 감상문에 등록된 댓글이 아닙니다.");
        }

        if (!comment.getUser().getNickname().equals(userNickname)){
            throw new RuntimeException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}
