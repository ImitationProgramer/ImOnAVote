package com.application.ioav.search.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.ioav.search.dto.SearchDTO;

@Mapper
public interface SearchDAO {
	
	public int getsearchKeyword(String memberId);
	public List<SearchDTO> selectListSearchKw(Map<String,String> searchKwMap);
	public void insertSearchKw(SearchDTO searchDTO);
	
}
