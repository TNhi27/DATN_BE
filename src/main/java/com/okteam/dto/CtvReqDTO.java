package com.okteam.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CtvReqDTO {
	String username;
	String password;
	String email;
	String sdt;
	boolean active=false;
	String fullname;
	String address;
	String sex="Khác";
	String verify=null;
	Date createdate= new Date();
	String image= null;
	int money=0;

}
