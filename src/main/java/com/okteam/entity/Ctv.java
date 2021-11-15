package com.okteam.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="ctv")
@Data
public class Ctv {
	@Id
	String username;
	String password;
	String email;
	String sdt;
	boolean active = false;
	String fullname = "Ẩn danh";
	String address;
	String sex;
	String verify;
	@Temporal(TemporalType.DATE)
	Date createdate = new Date();
	String image;
	int money = 0;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ctv")
	List<RegiProducts> list_regi;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ctv")
	List<Orders> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "fl_ctv")
	List<FollowSell> followSell;

	@JsonIgnore
	@OneToMany(mappedBy = "ctv_cmt")
	List<Comments> comments;

	
}
