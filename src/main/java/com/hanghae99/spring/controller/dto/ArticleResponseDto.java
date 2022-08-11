package com.hanghae99.spring.controller.dto;

import com.hanghae99.spring.entity.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ArticleResponseDto {
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Long id;
	private String userWriter;
	private String title;
	private int heartCnt;
	private int commentCnt;

	public ArticleResponseDto(Article article) {
		this.createdAt = article.getCreatedAt();
		this.modifiedAt = article.getModifiedAt();
		this.id = article.getId();
		this.userWriter = article.getUserWriter();
		this.title = article.getTitle();
		this.heartCnt = article.getHeartList().size();
		this.commentCnt = article.getCommentList().size();
	}

}
