package kr.co.dreamboard.entity;

import lombok.Data;

@Data
public class Member {
	private int memIdx;
	private String memID;
	private String memPwd;
	private String memName;
	private int memAge;
	private String memGender;
	private String memEmail;
	private String memAddr;
	private String memProfile;
	private boolean enabled;
}

