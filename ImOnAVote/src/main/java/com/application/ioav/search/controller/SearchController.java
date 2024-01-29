package com.application.ioav.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.ioav.member.service.MemberService;
import com.application.ioav.search.service.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private MemberService memberService;
	
	
}
