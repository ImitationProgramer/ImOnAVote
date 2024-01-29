package com.application.ioav.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.ioav.search.dao.SearchDAO;
import com.application.ioav.search.dto.SearchDTO;

@Service
public class SearchServiceImpl implements SearchService{

	
	
	@Autowired
	private SearchDAO searchDAO;
	
	
	@Override
	public int searchKeyword(String memberId) {
		return searchDAO.getsearchKeyword(memberId);
	}
	
	@Override
	public List<SearchDTO> selectListSearchKw(Map<String,String>searchKwMap) {
		return searchDAO.selectListSearchKw(searchKwMap);
	}

	@Override
	public void insertSearchKw(SearchDTO searchDTO) {
		searchDAO.insertSearchKw(searchDTO);
	}

}
