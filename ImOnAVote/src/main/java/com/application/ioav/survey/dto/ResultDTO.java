package com.application.ioav.survey.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ResultDTO {
	
	private long resultId;
	private String userId;
	private long surveyId;
	private String answer;
	private String doYn;
}
