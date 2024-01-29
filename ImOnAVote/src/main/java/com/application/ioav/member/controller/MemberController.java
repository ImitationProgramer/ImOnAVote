package com.application.ioav.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.application.ioav.member.dto.MemberDTO;
import com.application.ioav.member.service.MemberService;
import com.application.ioav.survey.service.SurveyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Value("${file.repo.path}")
	private String fileRepositoryPath;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SurveyService surveyService;
	
	
	@GetMapping("/main")
	public ModelAndView main( HttpServletRequest request,
			@RequestParam(name="searchKeyword", defaultValue = "total") String searchKeyword,
            @RequestParam(name="searchWord", defaultValue="")String searchWord,
            @RequestParam(name="onePageViewCnt", defaultValue="10")int onePageViewCnt,
            @RequestParam(name="currentPageNumber", defaultValue="1")int currentPageNumber) {
		HttpSession session = request.getSession();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("member/main");
		Map<String, String> searchCntMap = new HashMap<String, String>();
		searchCntMap.put("searchKeyword", searchWord);
		searchCntMap.put("searchWord", searchWord);
		
		int allSurveyCnt = surveyService.getAllSurveyCnt(searchCntMap);
		int allPageCnt = allSurveyCnt / onePageViewCnt + 1;
		
		if(allSurveyCnt % onePageViewCnt == 0) allPageCnt--;
		
		int startPage = (currentPageNumber - 1) / 10 * 10 +1;
		if(startPage == 0) startPage=1;
		
		int endPage = startPage + 9;
		
		if(endPage > allPageCnt) endPage = allPageCnt;
		
		int startBoardIdx = (currentPageNumber - 1) * onePageViewCnt;
	
		mv.addObject("startPage"         , startPage);
		mv.addObject("endPage"           , endPage);
		mv.addObject("allSurveyCnt"   	 , allSurveyCnt);
		mv.addObject("allPageCnt"   	 , allPageCnt);
		mv.addObject("onePageViewCnt"    , onePageViewCnt);
		mv.addObject("currentPageNumber" , currentPageNumber);
		mv.addObject("startBoardIdx"     , startBoardIdx);
		mv.addObject("searchKeyword"     , searchKeyword);
		mv.addObject("searchWord"        , searchWord);
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("onePageViewCnt" , onePageViewCnt);
		searchMap.put("startBoardIdx"  , startBoardIdx);
		searchMap.put("searchKeyword"  , searchKeyword);
		searchMap.put("searchWord"     , searchWord);
		mv.addObject("surveyList"      , surveyService.getSurveyList(searchMap));
		mv.addObject("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		return mv;
	}
	
	@GetMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("member/register");
	}
	
	@PostMapping("/register")
	public String register(@RequestParam("uploadProfile") MultipartFile uploadProfile, @ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException{
		System.out.println("controller : " + memberDTO);
		memberService.insertMember(uploadProfile,memberDTO);
		return "redirect:login";
	}
	
	@GetMapping("/login")
	public ModelAndView loginMember() {
		return new ModelAndView("member/login");
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String loginMember(MemberDTO memberDTO, HttpServletRequest request) {
		String isValidMember="n";
		if(memberService.loginMember(memberDTO)) {
			HttpSession session =request.getSession();
			session.setAttribute("memberId", memberDTO.getMemberId());
			
			isValidMember ="y";
		}
		return isValidMember;
	}
	
	@GetMapping("/logout")
	public String logoutMember(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		
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
	@GetMapping("/modify")
	public ModelAndView modify(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("member/modify");
		mv.addObject("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		return mv;
		
	}
	@GetMapping("/thumbnails")
    @ResponseBody
    public Resource thumbnails(@RequestParam("fileName") String fileName) throws IOException{
        return new UrlResource("file:" + fileRepositoryPath + fileName);
    }
	
	@PostMapping("/modify")
	public String modify(@RequestParam("uploadProfile") MultipartFile uploadProfile, @ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		if (memberDTO.getSmsstsYn() == null)   memberDTO.setSmsstsYn("n");
		if (memberDTO.getEmailstsYn() == null) memberDTO.setEmailstsYn("n");
		memberService.modifyMember(uploadProfile, memberDTO);
		return "redirect:main";
	}
	
	@GetMapping("/remove")
	public ModelAndView remove() {
		return new ModelAndView("member/remove");
	}
	
	@PostMapping("/remove")
	public String remove(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		memberService.modifyInactiveMember((String)session.getAttribute("memberId"));
		
		session.invalidate();
		return "redirect:main";
	}
	
	@GetMapping("/adminMember")
	public ModelAndView memberList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("member/adminMember");
		mv.addObject("memberList", memberService.getMemberList());
		mv.addObject("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		return mv;
	}
	
	@GetMapping("/searchMemberList")
	@ResponseBody
	public List<MemberDTO> searchMemberList(@RequestParam Map<String,String> searchMap){
		
		return memberService.getMemberSearchList(searchMap);
	}
	
	@GetMapping("/forgetPassword")
	public ModelAndView forgetPassword() {
		return new ModelAndView("member/forgetPassword");
	}
	
	@PostMapping("/forgetPassword")
	@ResponseBody
	public String forgetPassword(MemberDTO memberDTO, HttpServletRequest request) {
		String isValidMember="n";
		if(memberService.loginMember(memberDTO)) {
			HttpSession session =request.getSession();
			session.setAttribute("email", memberDTO.getEmail());
			
			isValidMember ="y";
		}
		return isValidMember;
	}
	
}
