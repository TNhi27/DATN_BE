package com.okteam.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CtvReqDTO {
	String username;
	String password;
	String email;
	String sdt;
	boolean active;
	String fullname;
	String address;
	String sex;
	String verify;
	Date createdate;
	String image;
	int money;

}
