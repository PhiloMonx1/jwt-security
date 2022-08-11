package com.hanghae99.spring.controller;

import com.hanghae99.spring.controller.dto.ArticleRequestDto;
import com.hanghae99.spring.controller.dto.ArticleResponseDto;
import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {
	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/articles")
	public List<ArticleResponseDto> readAllPost(){
		return articleService.readAllArticle();
	}

	@GetMapping("/articles/{articleId}")
	public Article readArticle(@PathVariable Long articleId){
		return articleService.readArticle(articleId);
	}

	@PostMapping("/auth/articles")
	public Article creatArticle(@RequestBody ArticleRequestDto articleRequestDto){
		return articleService.creatArticle(articleRequestDto);
	}

//	@PostMapping("/auth/articles/image/{articleId}")
//	public String upload(@PathVariable Long articleId, MultipartFile multipartFile, String dirName) throws IOException {
//		return s3Uploader.upload(articleId, multipartFile, "img") + " 등록 완료.";
//	}

	@PatchMapping("/auth/articles/{articleId}")
	public Article updateArticle(@PathVariable Long articleId, @RequestBody ArticleRequestDto articleRequestDto){
		return articleService.updateArticle(articleId, articleRequestDto);
	}

	@DeleteMapping("/auth/articles/{articleId}")
	public String deleteArticle(@PathVariable Long articleId){
		return articleService.deleteArticle(articleId);
	}
}
