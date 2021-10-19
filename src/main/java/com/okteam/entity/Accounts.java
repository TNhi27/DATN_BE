package com.okteam.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Accounts {
	@Id
	String username;
	String password;
	String sdt;
	int money;
	String address;
	String email;
	boolean active=true;
	
	@JsonManagedReference
	@OneToOne(mappedBy = "acc_ctv")
	Ctv ctv;
	
	
	@JsonManagedReference
	@OneToOne(mappedBy = "acc_ncc")
	Ncc ncc;
	
	@JsonBackReference
	@OneToOne(mappedBy = "acc_admin")
	Admin admin;
	
	@JsonManagedReference //thành phần chính trong mối quan hệ trong serialization, vì vậy chúng sẽ được serialized bình thường
	@OneToMany(mappedBy = "acc_cmt")
	List<Comments> comments;
	
	@JsonBackReference
	@OneToMany(mappedBy = "acc_tran")
	List<Transaction> transaction;
	
}
