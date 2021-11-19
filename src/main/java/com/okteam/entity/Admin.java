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
@Table(name = "Admin")
@Data
public class Admin {

	@Id
	String username;
	String password;
	String email;
	String sdt;
	boolean active = true;
	String fullname;
	String address;
	String sex;
	String verify;
	@Temporal(TemporalType.DATE)
	Date createdate = new Date();

	@JsonIgnore
	@OneToMany(mappedBy="acc_post")
	List<Post> posts;
}
