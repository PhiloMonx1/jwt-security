package com.hanghae99.spring.repository;

import com.hanghae99.spring.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRrepository extends JpaRepository<Comment, Long> {
}
