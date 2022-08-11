package com.hanghae99.spring.controller;

import com.hanghae99.spring.S3.S3Uploader;
import com.hanghae99.spring.controller.dto.ArticleRequestDto;
import com.hanghae99.spring.controller.dto.ArticleResponseDto;
import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {
	private final ArticleService articleService;
	private final S3Uploader s3Uploader;

	@Autowired
	public ArticleController(ArticleService articleService, S3Uploader s3Uploader) {
		this.articleService = articleService;
		this.s3Uploader = s3Uploader;
	}

	@GetMapping("/articles")
	public List<ArticleResponseDto> readAllPost(){
		return articleService.readAllArticle();
	}

	@GetMapping("/article/{articleId}")
	public Article readArticle(@PathVariable Long articleId){
		return articleService.readArticle(articleId);
	}

	@PostMapping("/auth/article")
	public Article creatArticle(@RequestBody ArticleRequestDto articleRequestDto){
		return articleService.creatArticle(articleRequestDto);
	}

	@PostMapping("/auth/article/image/{articleId}")
	public String upload(@PathVariable Long articleId, MultipartFile multipartFile, String dirName) throws IOException {

		System.out.println(articleId);

		System.out.println(multipartFile);
		System.out.println(multipartFile.getName());

		return s3Uploader.upload(articleId, multipartFile, "img");
	}

	@PatchMapping("/auth/article/{articleId}")
	public String updateArticle(@PathVariable Long articleId, @RequestBody ArticleRequestDto articleRequestDto){
		return articleService.updateArticle(articleId, articleRequestDto);
	}

	@DeleteMapping("/auth/article/{articleId}")
	public String deleteArticle(@PathVariable Long articleId){
		return articleService.deleteArticle(articleId);
	}
}
