package com.application.ioav.survey.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SurveyDTO {
	private long surveyId;
	private String surveyNm;
	private String writer;
	private String content;
	private Date postDt;
	private Date dueDt;
	private long givePoint;
	private String keyword;
	private String payYn;
}
