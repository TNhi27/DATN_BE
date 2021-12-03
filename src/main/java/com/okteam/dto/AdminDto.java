package com.okteam.dto;

import java.util.Date;


import lombok.Data;

@Data
public class AdminDto {
	String username;
	String password;
	String email;
	String sdt;
	boolean active = true;
	String fullname;
	String address;
	String sex;
	String image;
	Date createdate = new Date();
}
