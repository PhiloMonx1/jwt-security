package com.hanghae99.spring.service;

import com.hanghae99.spring.controller.dto.MemberResponseDto;
import com.hanghae99.spring.repository.MemberRepository;
import com.hanghae99.spring.util.SecurityUtil;
import com.hanghae99.spring.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long getSigninUserId(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.valueOf(userId);
    }
    public Member getSinginUser(){
        return memberRepository.findById(getSigninUserId())
            .orElseThrow(()-> new RuntimeException("유저를 찾지 못했습니다."));
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String email) {
        return memberRepository.findByUsername(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }
}
