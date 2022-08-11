package com.hanghae99.spring.service;

import com.hanghae99.spring.controller.dto.ArticleMyDto;
import com.hanghae99.spring.controller.dto.CommentMyDto;
import com.hanghae99.spring.controller.dto.MypageDto;
import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.entity.Comment;
import com.hanghae99.spring.entity.Heart;
import com.hanghae99.spring.entity.Member;
import com.hanghae99.spring.repository.ArticleRepository;
import com.hanghae99.spring.repository.CommentRepository;
import com.hanghae99.spring.repository.HeartRepository;
import com.hanghae99.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageService {
	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final ArticleRepository articleRepository;
	private final CommentRepository commentRepository;
	private final HeartRepository heartRepository;

	@Autowired
	public MypageService(MemberRepository memberRepository, MemberService memberService, ArticleRepository articleRepository, CommentRepository commentRepository, HeartRepository heartRepository) {
		this.memberRepository = memberRepository;
		this.memberService = memberService;
		this.articleRepository = articleRepository;
		this.commentRepository = commentRepository;
		this.heartRepository = heartRepository;
	}


	public MypageDto readMypage() {
		MypageDto mypageDto = new MypageDto();

		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));

		List<Article> articleList = articleRepository.findAllByMember(member);
		List<Comment> commentList = commentRepository.findAllByMember(member);
		List<Heart> heartArticleList = heartRepository.findByMemberAndArticleIsNotNull(member);
		List<Heart> heartCommentList = heartRepository.findByMemberAndCommentIsNotNull(member);

		for (Article article : articleList) {
			mypageDto.addArticleMyDtoList(new ArticleMyDto(article));
		}
		for (Comment comment : commentList) {
			mypageDto.addCommentMyDtoList(new CommentMyDto(comment));
		}
		for (Heart heartArticle : heartArticleList) {
			mypageDto.addHeartArticleMyDtoList(new ArticleMyDto(heartArticle.getArticle()));
		}
		for (Heart heartComment : heartCommentList) {
			mypageDto.addHeartCommentMyDtoList(new CommentMyDto(heartComment.getComment()));
		}

		return mypageDto;
	}
}