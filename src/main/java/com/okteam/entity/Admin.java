package com.okteam.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.okteam.dto.AdminDto;

import lombok.Data;

@Entity
@Table(name = "Admin")
@Data
public class Admin {

	@Id
	String username;
	@JsonIgnore
	String password;
	String email;
	String sdt;
	boolean active = true;
	String fullname;
	String address;
	String sex;
	String image;
	@Temporal(TemporalType.DATE)
	Date createdate = new Date();

	@JsonBackReference
	@OneToMany(mappedBy = "acc_post")
	List<Post> posts; // chưa tạo liên kết trong csdl
	
	public Admin dtoReturnEntity(AdminDto ad) {
		this.username = ad.getUsername();
		this.password = ad.getPassword();
		this.email = ad.getEmail();
		this.sdt = ad.getSdt();
		this.active = ad.isActive();
		this.fullname = ad.getFullname();
		this.address = ad.getAddress();
		this.sex = ad.getSex();
		this.image = ad.getImage();
		return this;
	}
	
}
