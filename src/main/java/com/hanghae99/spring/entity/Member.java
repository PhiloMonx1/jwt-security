package com.hanghae99.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae99.spring.util.Timestamped;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member extends Timestamped {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany//(mappedBy = "member") -> 조회가 많이 일어나고 데이터의 생성 삭제가 별로 일어나지 않는 경우
    @JsonManagedReference
    private List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "member")// -> 생성 삭제가 많이 일어나는 경우
    @JsonManagedReference
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Heart> heartList = new ArrayList<>();

    @Builder
    public Member(String username, String password, Authority authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public void addArticle(Article article) {
        this.articleList.add(article);
    }
    public void removeArticle(Article article) {
        this.articleList.remove(article);
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
    public void removeComment(Comment comment) {
        this.commentList.remove(comment);
    }

    public void addHeart(Heart heart) {
        this.heartList.add(heart);
    }
    public void removeHeart(Heart heart) {
        this.heartList.remove(heart);
    }
}
