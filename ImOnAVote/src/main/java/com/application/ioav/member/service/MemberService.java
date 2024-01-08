package com.application.ioav.member.service;

import java.util.List;
import java.util.Map;

import com.application.ioav.member.dto.MemberDTO;

public interface MemberService {

	public void insertMember(MemberDTO memberDTO);
	public String checkValidId(String memberId);
	public String checkDuplicateName(MemberDTO memberDTO);
	public boolean loginMember(MemberDTO memberDTO);

	public MemberDTO getMemberDetail(String memberId);
	public void modifyMember(MemberDTO memberDTO);
	public void modifyInactiveMember(String memberId);
	
	public List<MemberDTO> getMemberList();
	public List<MemberDTO> getMemberSearchList(Map<String, String> searchMap);
	public void deleteMemberScheduler();
	
	
}