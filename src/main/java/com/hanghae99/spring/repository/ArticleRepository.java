package com.hanghae99.spring.repository;

import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findAllByOrderByModifiedAtDesc();

	List<Article> findAllByMember(Member member);
}
