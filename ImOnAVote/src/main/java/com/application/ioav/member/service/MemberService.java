package com.application.ioav.member.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.application.ioav.member.dto.MemberDTO;

public interface MemberService {

	public void insertMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException;
	public String checkValidId(String memberId);
	public String checkDuplicateName(MemberDTO memberDTO);
	public boolean loginMember(MemberDTO memberDTO);
	public int getAge(String memberId);
	public String getName(String memberId);
	public String getId(String memberId);
	public MemberDTO getMemberDetail(String memberId);
	public void modifyMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException;
	public void modifyInactiveMember(String memberId);
	
	public List<MemberDTO> getMemberList();
	public List<MemberDTO> getMemberSearchList(Map<String, String> searchMap);

	public void deleteMemberScheduler();
	
	
}