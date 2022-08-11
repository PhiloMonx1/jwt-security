package com.hanghae99.spring.service;

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

@Service
public class HeartService {
	private final HeartRepository heartRepository;
	private final MemberRepository memberRepository;
	private final ArticleRepository articleRepository;
	private final CommentRepository commentRepository;
	private final MemberService memberService;

	@Autowired
	public HeartService(HeartRepository heartRepository, MemberRepository memberRepository, ArticleRepository articleRepository, CommentRepository commentRepository, MemberService memberService) {
		this.heartRepository = heartRepository;
		this.memberRepository = memberRepository;
		this.articleRepository = articleRepository;
		this.commentRepository = commentRepository;
		this.memberService = memberService;
	}


	public String heartArticle(Long postId) {
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));
		Article article = articleRepository.findById(postId)
				.orElseThrow(()-> new NullPointerException("해당 게시물이 존재하지 않습니다."));

		if(heartRepository.findByMemberAndArticle(member, article) == null){
			Heart heart = new Heart(member, article);
			member.addHeart(heart);
			article.addHeart(heart);
			article.setHeartCnt(article.getHeartList().size());
			heartRepository.save(heart);
			return article.getId() + "번 게시물 좋아요" + ", 총 좋아요 수 : " + article.getHeartCnt();
		}else  {
			Heart heart = heartRepository.findByMemberAndArticle(member, article);
			member.removeHeart(heart);
			article.removeHeart(heart);
			article.setHeartCnt(article.getHeartList().size());
			heartRepository.delete(heart);
			return article.getId() + "번 게시물 좋아요 취소" + ", 총 좋아요 수 : " + article.getHeartCnt();
		}
	}

	public String heartComment(Long commentId) {
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()-> new NullPointerException("해당 댓글이 존재하지 않습니다."));

		if(heartRepository.findByMemberAndComment(member, comment) == null){
			Heart heart = new Heart(member, comment);
			member.addHeart(heart);
			comment.addHeart(heart);
			comment.setHeartCnt(comment.getHeartList().size());
			heartRepository.save(heart);
			return comment.getId() + "번 댓글 좋아요" + ", 총 좋아요 수 : " + comment.getHeartCnt();
		}else  {
			Heart heart = heartRepository.findByMemberAndComment(member, comment);
			member.removeHeart(heart);
			comment.removeHeart(heart);
			comment.setHeartCnt(comment.getHeartList().size());
			heartRepository.delete(heart);
			return comment.getId() + "번 댓글 좋아요 취소" + ", 총 좋아요 수: " + comment.getHeartCnt();
		}
	}
}
