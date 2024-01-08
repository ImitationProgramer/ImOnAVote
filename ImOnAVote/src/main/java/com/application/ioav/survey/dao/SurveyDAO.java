package com.application.ioav.survey.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.ioav.survey.dto.SurveyDTO;

@Mapper
public interface SurveyDAO {
	
	public List<SurveyDTO> getSurveyList(Map<String, Object> searchMap); 
	public SurveyDTO getSurveyDetail(long surveyId); 
	public String kwSearchSurvey(String string); 
	public void insertSurvey(SurveyDTO surveyDTO);
	public void updateSurvey(SurveyDTO surveyDTO); 
	public void deleteSurvey(long surveyId); 
	public int getReadCnt(long surveyId);
	public String getCheckPayYn(long surveyId);
}
