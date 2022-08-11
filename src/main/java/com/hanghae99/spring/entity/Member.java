package com.hanghae99.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member {

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

    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
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
}
