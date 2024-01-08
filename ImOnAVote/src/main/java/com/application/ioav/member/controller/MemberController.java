package com.application.ioav.member.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.application.ioav.member.dto.MemberDTO;
import com.application.ioav.member.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/main")
	public ModelAndView main() {
		return new ModelAndView("member/main");
	}
	
	@GetMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("member/register");
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute MemberDTO memberDTO){
		System.out.println("controller : " + memberDTO);
		memberService.insertMember(memberDTO);
		return "redirect:main";
	}
	
	@PostMapping("/validId")
	@ResponseBody
	public String validId(@RequestParam("memberId") String memberId) {
		return memberService.checkValidId(memberId);
	}
	
	@PostMapping("/validNm")
	@ResponseBody
	public String validNm(MemberDTO memberDTO) {
		return memberService.checkDuplicateName(memberDTO);
	}
}
