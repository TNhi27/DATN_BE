package com.okteam.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "ncc")
@Data
public class Ncc {
	@Id
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
	@Temporal(TemporalType.DATE)
	Date createdate = new Date();
	String nccname = "Ẩn danh";
	String ncclogo;
	String description = "Xin chào";

	int money = 0;

	@JsonIgnore
	@OneToMany(mappedBy = "ncc")
	List<Products> products;

	@JsonIgnore
	@OneToMany(mappedBy = "ncc")
	List<Orders> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "fl_ncc")
	List<FollowSell> followSell;

}
