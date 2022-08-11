package com.hanghae99.spring.controller.dto;

import com.hanghae99.spring.entity.Article;
import lombok.Getter;

@Getter
public class ArticleMyDto {
	private final String title;
	private final int heartCnt;
	private final int commentCnt;

	public ArticleMyDto(Article article) {
		this.title = article.getTitle();
		this.heartCnt = article.getHeartList().size();
		this.commentCnt = article.getCommentList().size();
	}
}
