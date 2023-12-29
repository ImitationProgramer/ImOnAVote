package com.application.ioav.survey.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SurveyDTO {
	private String surveyId;
	private String surveyNm;
	private String content;
	private Date postDt;
	private Date dueDt;
	private long givePoint;
	private String keyword;
}
