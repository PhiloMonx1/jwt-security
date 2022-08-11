package com.hanghae99.spring.controller.dto;

import com.hanghae99.spring.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
	private final LocalDateTime createdAt;
	private final LocalDateTime modifiedAt;
	private final Long id;
	private final String userWriter;
	private final String content;
	private final int heartCnt;
	private final int commentCnt;

	public CommentResponseDto(Comment comment) {
		this.createdAt = comment.getCreatedAt();
		this.modifiedAt = comment.getModifiedAt();
		this.id = comment.getId();
		this.userWriter = comment.getUserWriter();
		this.content = comment.getContent();
		this.heartCnt = comment.getHeartList().size();
		this.commentCnt = comment.getChildren().size();
	}
}
