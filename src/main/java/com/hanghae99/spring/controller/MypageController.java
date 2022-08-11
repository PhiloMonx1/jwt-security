package com.hanghae99.spring.controller;

import com.hanghae99.spring.controller.dto.MypageDto;
import com.hanghae99.spring.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MypageController {

	private final MypageService mypageService;

	@Autowired
	public MypageController(MypageService mypageService) {
		this.mypageService = mypageService;
	}

	@GetMapping("/auth/mypage")
	public MypageDto readMypage() {
		return mypageService.readMypage();
	}
}
