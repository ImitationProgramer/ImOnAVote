package com.application.ioav.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.ioav.member.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	public void insertMember(MemberDTO memberDTO);
}
