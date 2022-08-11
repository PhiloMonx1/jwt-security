package com.hanghae99.spring.util;

import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {
	private final ArticleRepository articleRepository;

	@Autowired
	public Scheduler(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@Scheduled(cron = "* * 1 * * *")
	public void updatePrice() {
		System.out.println("스케줄러 작동");
		List<Article> articleList = articleRepository.findAll();
		for (Article article : articleList) {
			if (article.getCommentCnt() <= 0) {
				articleRepository.deleteById(article.getId());
				System.out.println(article.getId() + "번 게시물" + article.getTitle() + " 스케줄러에 의해 삭제되었습니다.");
			}
		}
		System.out.println("스케줄러 종료");
	}
}
