package com.hanghae99.spring.repository;

import com.hanghae99.spring.entity.Comment;
import com.hanghae99.spring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByArticle_Id(Long articleId);

	List<Comment> findAllByMember(Member member);
}
