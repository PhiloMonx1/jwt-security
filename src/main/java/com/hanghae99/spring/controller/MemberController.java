package com.hanghae99.spring.controller;

import com.hanghae99.spring.controller.dto.MemberResponseDto;
import com.hanghae99.spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        return ResponseEntity.ok(memberService.getMyInfo());
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable String username) {
        return ResponseEntity.ok(memberService.getMemberInfo(username));
    }
}