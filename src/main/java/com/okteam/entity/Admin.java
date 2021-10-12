package com.okteam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "Admin")
@Data
public class Admin {

	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	int idadmin;
	String fullname;
	boolean active = true;
	@JsonManagedReference
	@OneToOne @JoinColumn(name = "username")
	Accounts acc_admin;
}
