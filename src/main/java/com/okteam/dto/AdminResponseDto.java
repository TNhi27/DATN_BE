package com.okteam.dto;

import java.util.Date;
import java.util.List;

import com.okteam.entity.Admin;
import com.okteam.entity.Post;

import lombok.Data;

@Data
public class AdminResponseDto {
	String username;
	String email;
	String sdt;
	boolean active = true;
	String fullname;
	String address;
	String sex;
	String image;
	Date createdate = new Date();
	List<Post> posts; // chưa tạo liên kết trong csdl
	
	public AdminResponseDto createByEntity(Admin ad) {
		this.username = ad.getUsername();
		this.email = ad.getEmail();
		this.sdt = ad.getSdt();
		this.active = ad.isActive();
		this.fullname = ad.getFullname();
		this.address = ad.getAddress();
		this.sex = ad.getSex();
		this.image = ad.getImage();
		this.createdate = ad.getCreatedate();
//		this.posts = ad.getPosts();
		return this;
	}
	
}
