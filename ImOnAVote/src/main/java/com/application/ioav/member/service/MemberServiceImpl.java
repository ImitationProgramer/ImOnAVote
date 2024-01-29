package com.application.ioav.member.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.ioav.member.dao.MemberDAO;
import com.application.ioav.member.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService{
	@Value("${file.repo.path}")
	private String fileRepositoryPath;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@Override
	public void insertMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException{
		if(!uploadProfile.getOriginalFilename().isEmpty()) {
			String originalFilename = uploadProfile.getOriginalFilename();
			memberDTO.setProfileOriginalName(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID() + extension;
			memberDTO.setProfileUUID(uploadFile);
			
			uploadProfile.transferTo(new File(fileRepositoryPath + uploadFile));
		}
		
		if(memberDTO.getSmsstsYn() == null)	memberDTO.setSmsstsYn("n");
		if(memberDTO.getEmailstsYn()==null) memberDTO.setEmailstsYn("n");
		System.out.println("service : " + memberDTO);
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		memberDAO.insertMember(memberDTO);
	}

	@Override
	public String checkValidId(String memberId) {
		String isValidId = "y";
		if (memberDAO.getCheckValidId(memberId) != null) {
			isValidId = "n";
		}
		
		return isValidId;
	}

	@Override
	public String checkDuplicateName(MemberDTO memberDTO) {
		String isValidName="y";
		if(memberDAO.selectDuplicatedName(memberDTO) != null) {
			isValidName="n";
		}
		return isValidName;
	}
	
	@Override
	public boolean loginMember(MemberDTO memberDTO) {
		MemberDTO validateData = memberDAO.selectloginMember(memberDTO.getMemberId());
		if (validateData != null) {
			if (passwordEncoder.matches(memberDTO.getPasswd() , validateData.getPasswd()) && !validateData.getActiveYn().equals("n")) {
				return true;
			} 
		}
		return false;
	}


	@Override
	public MemberDTO getMemberDetail(String memberId) {
		return memberDAO.selectOneMember(memberId);
	}

	@Override
	public void modifyMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException{	
		
		if(!uploadProfile.getOriginalFilename().isEmpty()) {
			new File(fileRepositoryPath + memberDTO.getProfileUUID()).delete();
			
			String originalFilename = uploadProfile.getOriginalFilename();
			memberDTO.setProfileOriginalName(originalFilename);
			
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			
			String uploadFile = UUID.randomUUID()+extension;
			memberDTO.setProfileUUID(uploadFile);
			
			uploadProfile.transferTo(new File(fileRepositoryPath + uploadFile));
		}
		
		memberDAO.updateMember(memberDTO);
		
	}

	@Override
	public void modifyInactiveMember(String memberId) {
		memberDAO.updateInactiveMember(memberId);
		
	}

	@Override
	public List<MemberDTO> getMemberList() {
		return memberDAO.selectListMember();
	}

	@Override
	public List<MemberDTO> getMemberSearchList(Map<String, String> searchMap) {
		return memberDAO.selectListSearchMember(searchMap);
	}

	@Override
	@Scheduled(cron="0 0 0 1 * *")
	public void deleteMemberScheduler() {
		List<MemberDTO> deleteMemberList = memberDAO.selectListInActiveMember();
		if(!deleteMemberList.isEmpty())
			for(MemberDTO memberDTO : deleteMemberList)
				memberDAO.deleteMember(memberDTO.getMemberId());
	}

	@Override
	public int getAge(String memberId) {
		return memberDAO.getAge(memberId);
	}

	@Override
	public String getName(String memberId) {
		return memberDAO.getName(memberId);

	}

	@Override
	public String getId(String memberId) {
		
		return memberDAO.getId(memberId);
	}

	

}
