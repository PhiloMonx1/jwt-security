package com.hanghae99.spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Comment {
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
	private int commitCnt;

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
}
