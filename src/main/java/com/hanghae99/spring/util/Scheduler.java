package com.hanghae99.spring.util;

import com.hanghae99.spring.entity.Article;
import com.hanghae99.spring.entity.Member;
import com.hanghae99.spring.repository.ArticleRepository;
import com.hanghae99.spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class Scheduler {
	private final ArticleRepository articleRepository;
	private final MemberRepository memberRepository;

	@Autowired
	public Scheduler(ArticleRepository articleRepository,MemberRepository memberRepository) {
		this.articleRepository = articleRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional
	@Scheduled(cron = "30 * * * * *")
	public void updatePrice() {
		System.out.println("스케줄러 작동");
		List<Article> articleList = articleRepository.findAll();
		for (Article article : articleList) {
			if (article.getCommentCnt() <= 0) {
				Member member = memberRepository.findById(article.getMember().getId())
						.orElseThrow(()-> new IllegalArgumentException("잘못된 사용자입니다. 다시 로그인 후 시도해주세요."));
				member.removeArticle(article);
				articleRepository.deleteById(article.getId());
				System.out.println(article.getId() + "번 게시물" + article.getTitle() + " 스케줄러에 의해 삭제되었습니다.");
			}
		}
		System.out.println("스케줄러 종료");
	}
}
