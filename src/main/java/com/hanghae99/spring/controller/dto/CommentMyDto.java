package com.hanghae99.spring.controller.dto;

import com.hanghae99.spring.entity.Comment;
import lombok.Getter;

@Getter
public class CommentMyDto {
	private final String content;
	private final int heartCnt;
	private  int commentCnt;

	public CommentMyDto(Comment comment) {
		this.content = comment.getContent();
		this.heartCnt = comment.getHeartList().size();
		this.commentCnt = comment.getChildren().size();
	}
}
