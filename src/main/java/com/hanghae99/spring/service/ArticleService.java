package com.hanghae99.spring.service;

import com.hanghae99.spring.controller.dto.ArticleRequestDto;
import com.hanghae99.spring.controller.dto.ArticleResponseDto;
import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.entity.Member;
import com.hanghae99.spring.repository.ArticleRepository;
import com.hanghae99.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final MemberRepository memberRepository;
	private final MemberService memberService;

	@Autowired
	public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository, MemberService memberService) {
		this.articleRepository = articleRepository;
		this.memberRepository = memberRepository;
		this.memberService = memberService;
	}

	public List<ArticleResponseDto> readAllArticle() {
		List<Article> articleList = articleRepository.findAllByOrderByModifiedAtDesc();
		List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

		for(Article article : articleList){
			articleResponseDtoList.add(new ArticleResponseDto(article));
		}

		return articleResponseDtoList;
	}

	public Article readArticle(Long articleId) {
		return articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
	}

	public Article creatArticle(ArticleRequestDto articleRequestDto) {
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(()-> new IllegalArgumentException("잘못된 사용자입니다. 다시 로그인 후 시도해주세요."));
		Article article = new Article(articleRequestDto, member);

		member.addArticle(article);
		articleRepository.save(article);
		return article;
	}

	@Transactional
	public String updateArticle(Long articleId, ArticleRequestDto articleRequestDto) {
		Article article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(()-> new IllegalArgumentException("잘못된 사용자입니다. 다시 로그인 후 시도해주세요."));

		if(member.getUsername().equals(article.getUserWriter())){
			article.updatePost(articleRequestDto);
			return article.getId() + "번 게시글 수정 완료";
		}else return "작성자만 수정할 수 있습니다.";
	}

	public String deleteArticle(Long articleId) {
		Article article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(()-> new IllegalArgumentException("잘못된 사용자입니다. 다시 로그인 후 시도해주세요."));

		if(member.getUsername().equals(article.getUserWriter())){
			member.removeArticle(article);
			articleRepository.delete(article);
			return articleId + "번 게시물 삭제완료.";
		}else return "작성자만 삭제 할 수 있습니다.";
	}
}
