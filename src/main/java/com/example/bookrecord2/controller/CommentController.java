package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.CommentDto;
import com.example.bookrecord2.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentDto commentDto, @RequestParam Long reviewId/*, Principal principal*/) {
        String userNickname = "doy";
        commentService.addComment(commentDto, reviewId, userNickname /*principal.getName()*/);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestParam Long reviewId, @PathVariable Long commentId, @RequestBody CommentDto commentDto /*, Principal principal*/) {
        String userNickname = "doy";
        commentService.updateComment(reviewId, commentId, commentDto, userNickname);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@RequestParam Long reviewId, @PathVariable Long commentId /*, Principal principal*/) {
        String userNickname = "doy";
        commentService.deleteComment(reviewId, commentId, userNickname);
        return ResponseEntity.noContent().build();
    }
}
