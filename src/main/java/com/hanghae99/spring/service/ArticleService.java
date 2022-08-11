package com.hanghae99.spring.service;

import com.hanghae99.spring.controller.dto.ArticleRequestDto;
import com.hanghae99.spring.controller.dto.ArticleResponseDto;
import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.entity.Member;
import com.hanghae99.spring.repository.ArticleRrepository;
import com.hanghae99.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
	private ArticleRrepository articleRrepository;
	private MemberRepository memberRepository;
	private MemberService memberService;

	@Autowired
	public ArticleService(ArticleRrepository articleRrepository, MemberRepository memberRepository, MemberService memberService) {
		this.articleRrepository = articleRrepository;
		this.memberRepository = memberRepository;
		this.memberService = memberService;
	}

	public List<ArticleResponseDto> readAllArticle() {
		List<Article> articleList = articleRrepository.findAllByOrderByModifiedAtDesc();
		List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

		for(Article article : articleList){
			articleResponseDtoList.add(new ArticleResponseDto(article));
		}

		return articleResponseDtoList;
	}

	public Article readArticle(Long articleId) {
		return articleRrepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
	}

	public Article creatArticle(ArticleRequestDto articleRequestDto) {
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(()-> new IllegalArgumentException("잘못된 사용자입니다. 다시 로그인 후 시도해주세요."));
		Article article = new Article(articleRequestDto, member);

		member.addArticle(article);
		articleRrepository.save(article);
		return article;
	}

	@Transactional
	public Article updateArticle(Long articleId, ArticleRequestDto articleRequestDto) {
		Article article = articleRrepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

		article.updatePost(articleRequestDto);
		return article;
	}

	public String deleteArticle(Long articleId) {
		Article article = articleRrepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(()-> new IllegalArgumentException("잘못된 사용자입니다. 다시 로그인 후 시도해주세요."));

		member.removeArticle(article);

		articleRrepository.delete(article);
		return articleId + "번 게시물 삭제완료.";
	}
}
