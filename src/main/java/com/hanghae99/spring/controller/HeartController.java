package com.hanghae99.spring.controller;

import com.hanghae99.spring.service.HeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HeartController {
	private final HeartService heartService;

	@Autowired
	public HeartController(HeartService heartService) {
		this.heartService = heartService;
	}

	@PostMapping("/auth/article/heart/{postId}")
	private String heartArticle(@PathVariable Long postId){
		return heartService.heartArticle(postId);
	}

	@PostMapping("/auth/comment/heart/{commentId}")
	private String heartComment(@PathVariable Long commentId){
		return heartService.heartComment(commentId);
	}
}
