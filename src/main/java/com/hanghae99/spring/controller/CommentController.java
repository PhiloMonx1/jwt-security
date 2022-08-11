package com.hanghae99.spring.controller;

import com.hanghae99.spring.controller.dto.CommentRequestDto;
import com.hanghae99.spring.controller.dto.CommentResponseDto;
import com.hanghae99.spring.entity.Comment;
import com.hanghae99.spring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
	private final CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/comments/{articleId}")
	public List<CommentResponseDto> readAllComment(@PathVariable Long articleId) {
		return commentService.readAllComment(articleId);
	}

	@GetMapping("/comment/{commentId}")
	public Comment readComment(@PathVariable Long commentId) {
		return commentService.readComment(commentId);
	}

	@PostMapping("/auth/comment/{articleId}")
	public Comment creatComment(@PathVariable Long articleId, @RequestBody CommentRequestDto commentRequestDto) {
		return commentService.creatComment(articleId, commentRequestDto);
	}

	@PostMapping("/auth/recomment/{childId}")
	public Comment creatReComment(@PathVariable Long childId, @RequestBody CommentRequestDto commentRequestDto) {
		return commentService.creatReComment(childId, commentRequestDto);
	}

	@PatchMapping("/auth/comment/{commentId}")
	public String updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
		return commentService.updateComment(commentId, commentRequestDto);
	}

	@DeleteMapping("/auth/comment/{commentId}")
	public String deleteComment(@PathVariable Long commentId) {
		return commentService.deleteComment(commentId);
	}

	@DeleteMapping("/auth/recomment/{childId}")
	public String deleteReComment(@PathVariable Long childId) {
		return commentService.deleteReComment(childId);
	}
}
