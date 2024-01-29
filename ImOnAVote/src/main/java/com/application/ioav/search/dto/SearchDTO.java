package com.application.ioav.search.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class SearchDTO {

	private long resultId;
	private long age;
	private String keyword;
}
