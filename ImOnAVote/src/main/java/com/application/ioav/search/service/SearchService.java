package com.application.ioav.search.service;

import java.util.List;
import java.util.Map;

import com.application.ioav.search.dto.SearchDTO;

public interface SearchService {
	
	public int searchKeyword(String memberId);
	public List<SearchDTO> selectListSearchKw(Map<String,String>searchKwMap);
	public void insertSearchKw(SearchDTO searchDTO);
}
