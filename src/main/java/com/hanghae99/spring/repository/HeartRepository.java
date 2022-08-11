package com.hanghae99.spring.repository;

import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.entity.Comment;
import com.hanghae99.spring.entity.Heart;
import com.hanghae99.spring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
	Heart findByMemberAndArticle(Member member, Article article);

	Heart findByMemberAndComment(Member member, Comment comment);

	List<Heart> findByMemberAndArticleIsNotNull(Member member);

	List<Heart> findByMemberAndCommentIsNotNull(Member member);
}
