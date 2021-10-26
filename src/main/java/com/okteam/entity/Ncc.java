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

import lombok.Data;

@Entity
@Data
@Table(name="ncc")
public class Ncc {
	@Id
	String username;
	String password;
	String email;
	String sdt;
	boolean active;
	String fullname;
	String address;
	String sex;
	String veryfy;
	@Temporal(TemporalType.DATE)
	Date createdate;
	String nccname;
	String ncclogo;
	int money;
	
	
	
	@JsonBackReference
	@OneToMany(mappedBy = "ncc")
	List<Products> products;
	
	@JsonBackReference
	@OneToMany(mappedBy = "ncc")
	List<Orders> orders;

	@JsonBackReference
	@OneToMany(mappedBy = "fl_ncc")
	List<FollowSell> followSell;


	
}
