package com.hanghae99.spring.service;

import com.hanghae99.spring.controller.dto.CommentRequestDto;
import com.hanghae99.spring.controller.dto.CommentResponseDto;
import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.entity.Comment;
import com.hanghae99.spring.entity.Member;
import com.hanghae99.spring.repository.ArticleRepository;
import com.hanghae99.spring.repository.CommentRepository;
import com.hanghae99.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
	private final CommentRepository commentRepository;

	private final ArticleRepository articleRepository;
	private final MemberRepository memberRepository;
	private final MemberService memberService;

	@Autowired
	public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, MemberRepository memberRepository, MemberService memberService) {
		this.commentRepository = commentRepository;
		this.articleRepository = articleRepository;
		this.memberRepository = memberRepository;
		this.memberService = memberService;
	}


	public List<CommentResponseDto> readAllComment(Long articleId) {
		List<Comment> commentList = commentRepository.findAllByArticle_Id(articleId);

		List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

		for(Comment comment : commentList){
			commentResponseDtoList.add(new CommentResponseDto(comment));
		}

		return commentResponseDtoList;
	}

	public Comment readComment(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new NullPointerException("해당 댓글이 존재하지 않습니다."));
	}

	public Comment creatComment(Long articleId, CommentRequestDto commentRequestDto) {
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));

		Article article = articleRepository.findById(articleId)
				.orElseThrow(()-> new NullPointerException("해당 게시물이 존재하지 않습니다."));

		Comment comment = new Comment(commentRequestDto, article, member);


		member.addComment(comment);
		article.addComment(comment);
		article.setCommentCount(article.getCommentList().size());

		commentRepository.save(comment);
		return comment;
	}

	public Comment creatReComment(Long childId, CommentRequestDto commentRequestDto) {
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));

		Comment parent = commentRepository.findById(childId)
				.orElseThrow(()-> new NullPointerException("해당 게시물이 존재하지 않습니다."));

		Comment child = new Comment(commentRequestDto, parent, member);


		member.addComment(child);
		parent.addComment(child);
		parent.setCommentCount(parent.getChildren().size());

		commentRepository.save(child);
		return child;
	}

	@Transactional
	public String updateComment(Long commentId, CommentRequestDto commentRequestDto) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()-> new NullPointerException("해당 아이디가 존재하지 않습니다."));
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));

		if(member.getUsername().equals(comment.getUserWriter())){
			comment.updateComment(commentRequestDto);
			return comment.getId() + "번 댓글 수정 완료";
		}else return "작성자만 수정할 수 있습니다.";
	}

	public String deleteComment(Long commentId) {
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(()-> new NullPointerException("해당 댓글이 존재하지 않습니다."));

		Article article = articleRepository.findById(comment.getArticle().getId())
				.orElseThrow(()-> new NullPointerException("해당 게시물이 존재하지 않습니다."));

		if(member.getUsername().equals(comment.getUserWriter())){
			member.removeComment(comment);
			article.removeComment(comment);
			article.setCommentCount(article.getCommentList().size());
			commentRepository.delete(comment);
			return commentId + "번 댓글 삭제 완료";
		}else return "작성자만 삭제할 수 있습니다.";
	}

	public String deleteReComment(Long childId) {
		Member member = memberRepository.findById(memberService.getSigninUserId())
				.orElseThrow(() -> new NullPointerException("존재하지 않는 사용자입니다."));

		Comment child = commentRepository.findById(childId)
				.orElseThrow(()-> new NullPointerException("해당 댓글이 존재하지 않습니다."));

		Comment parent = commentRepository.findById(child.getParent().getId())
				.orElseThrow(()-> new NullPointerException("해당 게시물이 존재하지 않습니다."));

		if(member.getUsername().equals(child.getUserWriter())){
			member.removeComment(child);
			parent.removeComment(child);
			parent.setCommentCount(parent.getChildren().size());
			commentRepository.delete(child);
			return childId + "번 댓글 삭제 완료";
		}else return "작성자만 삭제할 수 있습니다.";
	}
}
