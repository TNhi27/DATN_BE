package com.okteam.dto;

import java.util.Date;

import lombok.Data;

@Data
public class NccDto {
	String username;
	String password;
	String email;
	String sdt;
	boolean active = false;
	String fullname = "Ẩn danh";
	String address;
	String city;
	String sex = "Khác";
	String verify=null;
	Date createdate = new Date();
	String nccname = "Ẩn danh";
	String ncclogo;
	String description = "Xin chào";
	String idghn;
	int money = 0;
}
