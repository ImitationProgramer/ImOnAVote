package com.application.ioav.member.dto;

import java.util.Date;

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
	private String activeYn;
	private Date inactiveAt;
	private long memberPoint;
}
