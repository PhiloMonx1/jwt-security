package com.hanghae99.spring.repository;

import com.hanghae99.spring.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRrepository extends JpaRepository<Article, Long> {
	List<Article> findAllByOrderByModifiedAtDesc();
}
