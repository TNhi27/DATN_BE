package com.okteam.entity;

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

import lombok.Data;

@Entity
@Data
@Table(name="ncc")
public class Ncc {
	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	int idncc;
	String nccname;
	String ncclogo;
	String description;
	
	@JsonBackReference
	@OneToOne @JoinColumn(name = "username")
	Accounts acc_ncc;
	
	@JsonBackReference
	@OneToMany(mappedBy = "ncc")
	List<Products> products;
	
	@JsonBackReference
	@OneToMany(mappedBy = "ncc")
	List<Orders> orders;
	
	
}
