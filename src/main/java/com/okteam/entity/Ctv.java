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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="ctv")
@Data
public class Ctv {
	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	int idctv;
	String fullname;
	String sex;
	String image;
	Date createdate;
	
	@JsonBackReference
	@OneToOne @JoinColumn(name = "username")
	Accounts acc_ctv;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "ctv")
	List<RegiProducts> list_regi;
	
	@JsonBackReference
	@OneToMany(mappedBy = "ctv")
	List<Orders> orders;

	@JsonBackReference
	@OneToMany(mappedBy = "fl_ctv")
	List<FollowSell> followSell;
}
