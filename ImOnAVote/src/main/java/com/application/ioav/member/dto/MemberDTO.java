package com.application.ioav.member.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MemberDTO {
	
	private String memberId;
	private String passwd;
	private String memberNm;
	private String sex;
	private String birthAt;
	private String hp;
	private String smsstsYn;
	private String email;
	private String emailstsYn;
	private long memberPoint;
	private String vipYn;
}
