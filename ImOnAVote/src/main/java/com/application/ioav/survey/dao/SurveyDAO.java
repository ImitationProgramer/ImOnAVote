package com.application.ioav.survey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.ioav.survey.dto.ResultDTO;
import com.application.ioav.survey.dto.SurveyDTO;

@Mapper
public interface SurveyDAO {
	
	public int getAllSurveyCnt(Map<String,String> searchCntMap);
	public List<SurveyDTO> getSurveyList(Map<String, Object> searchMap); 
	public SurveyDTO getSurveyDetail(long surveyId); 
	public String kwSearchSurvey(String string);
	public String getPasswd(long boardId);

	/* public String getWriter(String memberId); */
	public void insertSurvey(SurveyDTO surveyDTO);
	public void updateSurvey(SurveyDTO surveyDTO); 
	public void deleteSurvey(long surveyId); 
	public void updateReadCnt(long surveyId);
	public String getCheckPayYn(long surveyId);
	
	public List<ResultDTO> getResultList(long surveyId);
	public ResultDTO getResultDetail(long resultId);
	public void insertResult(ResultDTO resultDTO);
	public void updateResult(ResultDTO resultDTO);
	public String validResultUserCheck(ResultDTO resultDTO);
}
