package com.example.bookrecord2.repository;

import com.example.bookrecord2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
