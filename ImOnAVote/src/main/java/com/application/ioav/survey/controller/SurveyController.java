package com.application.ioav.survey.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.application.ioav.survey.dto.ResultDTO;
import com.application.ioav.survey.dto.SurveyDTO;
import com.application.ioav.survey.service.SurveyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/survey")
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/surveyList")
	public ModelAndView surveyList( HttpServletRequest request,
								   @RequestParam(name="searchKeyword", defaultValue = "total") String searchKeyword,
			                       @RequestParam(name="searchWord", defaultValue="")String searchWord,
			                       @RequestParam(name="onePageViewCnt", defaultValue="10")int onePageViewCnt,
			                       @RequestParam(name="currentPageNumber", defaultValue="1")int currentPageNumber) {
		HttpSession session = request.getSession();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("survey/surveyList");
		
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
	
	@GetMapping("/addSurvey")
	public ModelAndView addSurvey(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("survey/addSurvey");
		mv.addObject("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		return mv;
	}
	
	@PostMapping("/addSurvey")
	public String insertSurvey(SurveyDTO surveyDTO) {
		surveyService.addSurvey(surveyDTO);
		System.out.println(surveyDTO);
		return "redirect:/survey/surveyList";
	}
	
	@GetMapping("/surveyDetail")
	public ModelAndView surveyDetail(@RequestParam("surveyId") long surveyId) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("survey/surveyDetail");
		mv.addObject("surveyDTO",surveyService.getSurveyDetail(surveyId,true));
		return mv;
	}
	
	@GetMapping("/surveyAuthentication")
	public ModelAndView surveyAuthentication(@RequestParam("menu") String menu,
											 @RequestParam("memberId") String memberId,
											 @RequestParam("surveyId") long surveyId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("survey/surveyAuthentication");
		mv.addObject("memberDTO", memberService.getMemberDetail(memberId));
		mv.addObject("surveyDTO", surveyService.getSurveyDetail(surveyId, true));
		mv.addObject("menu",menu);
		return mv;
	}
	
	@PostMapping("/surveyAuthentication")
	@ResponseBody
	public String surveyAuthentication(@RequestParam("menu") String menu,
									   @ModelAttribute SurveyDTO surveyDTO,
									   @ModelAttribute MemberDTO memberDTO) {
		String jsScript = "";
		if(surveyService.checkAuthorizedUser(memberDTO, surveyDTO)) {
			if (menu.equals("update")) {
				jsScript = "<script>";
				jsScript += "location.href='/survey/modifySurvey?surveyId=" + surveyDTO.getSurveyId() + "';";
				jsScript += "</script>";
			}
			else if (menu.equals("delete")) {
				jsScript = "<script>";
				jsScript += "location.href='/survey/removeSurvey?surveyId=" +  surveyDTO.getSurveyId() + "';";
				jsScript += "</script>";
			}
			
		}
		else {
			 jsScript = "<script>";
			 jsScript += "alert('수정이나 삭제 권한이 없습니다!');";
			 jsScript += "history.go(-1);";
			 jsScript += "</script>";
		}
		return jsScript;
	}
	
	@GetMapping("/modifySurvey")
	public ModelAndView modifySurvey(@RequestParam("surveyId") long surveyId,
									 @RequestParam("writer") String writer) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("survey/modifySurvey");
		
		mv.addObject("surveyDTO",surveyService.getSurveyDetail(surveyId, false));
		return mv;
	}
	
	@PostMapping("/modifySurvey")
	public String modifySurvey(SurveyDTO surveyDTO) {
		surveyService.modifySurvey(surveyDTO);
		return "redirect:/survey/surveyList";
	}
	
	@GetMapping("/removeSurvey")
	public ModelAndView removeSurvey(@RequestParam("surveyId") long surveyId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("survey/removeSurvey");
		mv.addObject("surveyId",surveyId);
		
		return mv;
	}
	
	@PostMapping("/removeSurvey")
	public String postRemoveSurvey(@RequestParam("surveyId")long surveyId) {
		surveyService.removeSurvey(surveyId);
		return "redirect:/survey/surveyList";
	}
	
	@GetMapping("/addResult")
	public ModelAndView addResult(@RequestParam("surveyId") long surveyId) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("survey/addResult");
		mv.addObject("surveyId",surveyId);
		mv.addObject("surveyDTO", surveyService.getSurveyDetail(surveyId,false));
		return mv;
	}
	
	@PostMapping("/addResult")
	public String addResult(@ModelAttribute ResultDTO resultDTO){
		surveyService.addResult(resultDTO);
		return "redirect:/survey/surveyDetail?surveyId="+resultDTO.getSurveyId();
	}

	@GetMapping("/modifyResult")
	public ModelAndView modifyResult(@RequestParam("resultId")long resultId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("survey/modifyResult");
		mv.addObject("resultDTO" , surveyService.getResultDetail(resultId));
		
		return mv;
		
	}
	
	@PostMapping("/modifyResult")
	@ResponseBody
	public String modifyResult(ResultDTO resultDTO) {
		String jsScript = "";
		if (surveyService.modifyResult(resultDTO)) {
			jsScript += "<script>";
			jsScript += "location.href='/survey/surveyDetail?surveyId=" + resultDTO.getSurveyId() + "';";
			jsScript += "</script>";

		}
		else {
		   jsScript ="<script>"; 
		   jsScript += "alert('check your passowrd');";
		   jsScript += "history.go(-1);";
		   jsScript += "</script>";
		}
		
		return jsScript;
	}
}
