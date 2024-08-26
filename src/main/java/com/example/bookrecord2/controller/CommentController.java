package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.CommentDto;
import com.example.bookrecord2.service.CommentService;
import com.example.bookrecord2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentDto commentDto, @RequestParam Long reviewId, Principal principal) {
        String email = principal.getName();
        String nickname = userService.getNicknameByEmail(email);
        commentService.addComment(commentDto, reviewId, nickname);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestParam Long reviewId, @PathVariable Long commentId, @RequestBody CommentDto commentDto, Principal principal) {
        String email = principal.getName();
        String nickname = userService.getNicknameByEmail(email);
        commentService.updateComment(reviewId, commentId, commentDto, nickname);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@RequestParam Long reviewId, @PathVariable Long commentId, Principal principal) {
        String email = principal.getName();
        String nickname = userService.getNicknameByEmail(email);
        commentService.deleteComment(reviewId, commentId, nickname);
        return ResponseEntity.noContent().build();
    }
}
