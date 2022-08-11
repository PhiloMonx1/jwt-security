package com.hanghae99.spring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Heart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn
	private Member member;

	@ManyToOne
	@JoinColumn(name = "POST_ID")
	private Article article;

	@ManyToOne
	@JoinColumn(name = "COMMENT_ID")
	private Comment comment;
}
