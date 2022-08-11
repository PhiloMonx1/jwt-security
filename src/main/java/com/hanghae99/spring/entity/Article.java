package com.hanghae99.spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae99.spring.controller.dto.ArticleRequestDto;
import com.hanghae99.spring.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Article extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String userWriter;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	private int heartCnt;
	private int commentCnt;

	@ManyToOne()
	@JoinColumn
	@JsonBackReference
	private Member member;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Comment> commentList = new ArrayList<>();

	@OneToMany(mappedBy = "article")
	@JsonIgnore
	private List<Heart> heartList = new ArrayList<>();


	public Article(ArticleRequestDto articleRequestDto, Member member) {
		this.userWriter = member.getUsername();
		this.title = articleRequestDto.getTitle();
		this.content = articleRequestDto.getContent();
		this.member = member;
	}

	public void updatePost(ArticleRequestDto articleRequestDto) {
		this.title = articleRequestDto.getTitle();
		this.content = articleRequestDto.getContent();
	}

	public void addComment(Comment comment) {
		this.commentList.add(comment);
	}
	public void removeComment(Comment comment) {
		this.commentList.remove(comment);
	}

	public void setCommentCount(int commentListSize) {
		this.commentCnt = commentListSize;
	}
}
