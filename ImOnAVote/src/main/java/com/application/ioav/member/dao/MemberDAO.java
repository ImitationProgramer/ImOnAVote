package com.application.ioav.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.ioav.member.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	public void insertMember(MemberDTO memberDTO);
	public String getCheckValidId(String memberId);
	public String selectDuplicatedName(MemberDTO memberDTO);
	public MemberDTO selectloginMember(String string);

	public MemberDTO selectOneMember(String memberId);
	public void updateMember(MemberDTO memberDTO);
	public void updateInactiveMember(String memberId);
	
	public List<MemberDTO> selectListMember();
	public List<MemberDTO> selectListSearchMember(Map<String,String> searchMap);
	public List<MemberDTO> selectListInActiveMember();
	public void deleteMember(String memberId);
}
