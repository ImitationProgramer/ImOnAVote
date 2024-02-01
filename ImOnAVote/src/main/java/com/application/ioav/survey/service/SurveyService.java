package com.application.ioav.survey.service;

import java.util.List;
import java.util.Map;

import com.application.ioav.member.dto.MemberDTO;
import com.application.ioav.survey.dto.ResultDTO;
import com.application.ioav.survey.dto.SurveyDTO;

public interface SurveyService {

	public List<SurveyDTO> getSurveyList(Map<String, Object> searchMap);
	public int getAllSurveyCnt(Map<String, String> searchCntMap);
	public SurveyDTO getSurveyDetail(long surveyId, boolean isIncreaseReadCnt);
	public String kwSearchSurvey(String string);
	public void addSurvey(SurveyDTO surveyDTO);
	public boolean checkPayUser(long surveyId);
	public void modifySurvey(SurveyDTO surveyDTO);
	public void removeSurvey(long surveyId);
	public boolean checkAuthorizedUser(MemberDTO memberDTO, SurveyDTO surveyDTO);
	
	public List<ResultDTO> getResultList(long surveyId);
	public ResultDTO getResultDetail(long resultId);
	public void addResult(ResultDTO resultDTO);
	public boolean modifyResult(ResultDTO resultDTO);
}
