package com.hanghae99.spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae99.spring.controller.dto.CommentRequestDto;
import com.hanghae99.spring.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String userWriter;

	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn
	@JsonBackReference
	private Member member;

	@ManyToOne
	@JoinColumn
	@JsonBackReference
	private Article article;

	private int heartCnt;
	private int commentCnt;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonBackReference
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Comment parent;

	@OneToMany(mappedBy = "parent")
	@JsonManagedReference
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Comment> children = new ArrayList<>();

	@OneToMany(mappedBy = "comment", orphanRemoval = true)
	@JsonIgnore
	private List<Heart> heartList = new ArrayList<>();

	public Comment(CommentRequestDto commentRequestDto, Article article, Member member) {
		this.userWriter = member.getUsername();
		this.content = commentRequestDto.getContent();
		this.article = article;
		this.member = member;
	}

	public Comment(CommentRequestDto commentRequestDto, Comment parent, Member member) {
		this.userWriter = member.getUsername();
		this.content = commentRequestDto.getContent();
		this.parent = parent;
		this.member = member;
	}

	public void updateComment(CommentRequestDto commentRequestDto) {
		this.content = commentRequestDto.getContent();
	}

	public void addComment(Comment comment) {
		this.children.add(comment);
	}
	public void removeComment(Comment comment) {
		this.children.remove(comment);
	}

	public void addHeart(Heart heart) {
		this.heartList.add(heart);
	}
	public void removeHeart(Heart heart) {
		this.heartList.remove(heart);
	}

	public void setCommentCount(int commentListSize) {
		this.commentCnt = commentListSize;
	}
	public void setHeartCnt(int heartListSize) {
		this.heartCnt = heartListSize;
	}
}
