package org.minisecond.project.member;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberVO {
	
	private int memberNo;
	private String id;
	private String password;
	private String name;
	private String phoneNumber;
	private String postCode;
	private String address;
	private String detailAddress;
	private Date createdAt;
	private char deleteYn;
	private Date deletedAt;
	private String lockYn;
	private int loginFailure;
	private char boardAuth;
	private String searchValue;
	private Date birthDate;

}
