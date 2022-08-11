package com.hanghae99.spring.controller.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MypageDto {
	private List<ArticleMyDto> aticleMyDtoList = new ArrayList<>();
	private List<CommentMyDto> commentMyDtoList = new ArrayList<>();
	private List<ArticleMyDto> heartArticleMyDtoList = new ArrayList<>();
	private List<CommentMyDto> heartCommentMyDtoList = new ArrayList<>();

	public void addArticleMyDtoList(ArticleMyDto articleMyDto) {
		this.aticleMyDtoList.add(articleMyDto);
	}

	public void addCommentMyDtoList(CommentMyDto commentMyDto) {
		this.commentMyDtoList.add(commentMyDto);
	}

	public void addHeartArticleMyDtoList(ArticleMyDto heartArticleMyDto) {
		this.heartArticleMyDtoList.add(heartArticleMyDto);
	}

	public void addHeartCommentMyDtoList(CommentMyDto heartCommentMyDto) {
		this.heartCommentMyDtoList.add(heartCommentMyDto);
	}
}
