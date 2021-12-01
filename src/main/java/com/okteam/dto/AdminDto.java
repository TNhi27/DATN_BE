package com.okteam.dto;

import java.util.Date;

import com.okteam.entity.Admin;

import lombok.Data;

@Data
public class AdminDto {
	String username;
	String email;
	String sdt;
	boolean active = true;
	String fullname;
	String address;
	String sex;
	String image;
	Date createdate = new Date();
	
	public AdminDto createByEntity(Admin ad) {
		this.username = ad.getUsername();
		this.email = ad.getEmail();
		this.sdt = ad.getSdt();
		this.active = ad.isActive();
		this.fullname = ad.getFullname();
		this.address = ad.getAddress();
		this.sex = ad.getSex();
		this.image = ad.getImage();
		this.createdate = ad.getCreatedate();
		return this;
	}
	
}
