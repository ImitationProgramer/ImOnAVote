package com.application.ioav.survey.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ioav.member.dao.MemberDAO;
import com.application.ioav.member.dto.MemberDTO;
import com.application.ioav.survey.dao.SurveyDAO;
import com.application.ioav.survey.dto.ResultDTO;
import com.application.ioav.survey.dto.SurveyDTO;

@Service
public class SurveyServiceImpl implements SurveyService{

		@Autowired
		private SurveyDAO surveyDAO;

		
		@Override
		public List<SurveyDTO> getSurveyList(Map<String, Object> searchMap){
			System.out.println(searchMap);
			return surveyDAO.getSurveyList(searchMap);
		}

		@Override
		@Transactional
		public SurveyDTO getSurveyDetail(long surveyId, boolean isIncreaseReadCnt) {
			if(isIncreaseReadCnt) {
				surveyDAO.updateReadCnt(surveyId);
			}
			return surveyDAO.getSurveyDetail(surveyId);
		}

		@Override
		public String kwSearchSurvey(String string) {
			return surveyDAO.kwSearchSurvey(string);
		}

		@Override
		public void addSurvey(SurveyDTO surveyDTO) {
			surveyDAO.insertSurvey(surveyDTO);
		}

		@Override
		public boolean checkPayUser(long surveyId) {
			if(surveyDAO.getCheckPayYn(surveyId).equals("y")) {
				return true;
			}
			return false;
		}

		@Override
		public void modifySurvey(SurveyDTO surveyDTO) {
			surveyDAO.updateSurvey(surveyDTO);
		}

		@Override
		public void removeSurvey(long boardId) {
			surveyDAO.deleteSurvey(boardId);
		}

		@Override
		public int getAllSurveyCnt(Map<String, String> searchCntMap) {
			System.out.println(searchCntMap);
			return surveyDAO.getAllSurveyCnt(searchCntMap);
		}

		@Override
		public List<ResultDTO> getResultList(long surveyId) {
			return surveyDAO.getResultList(surveyId);
		}

		@Override
		public ResultDTO getResultDetail(long resultId) {
			return surveyDAO.getResultDetail(resultId);
		}

		@Override
		public void addResult(ResultDTO resultDTO) {
			surveyDAO.insertResult(resultDTO);
		}

		@Override
		public boolean modifyResult(ResultDTO resultDTO) {
			if(surveyDAO.validResultUserCheck(resultDTO).equals("y"))
				return true;
			return false;
		}

		@Override
		public boolean checkAuthorizedUser(MemberDTO memberDTO, SurveyDTO surveyDTO) {
			if(memberDTO.getMemberId().equals(surveyDTO.getWriter()))
				return true;
			return false;
		}

		/*
		 * @Override public boolean checkIfUserHasSurvey(String memberId) {
		 * if(memberDAO.getId(memberId).equals(surveyDAO.get)) }
		 */
}
