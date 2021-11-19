package com.okteam.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idorder;
	@Temporal(TemporalType.TIMESTAMP)
	Date dateorder;
	int total;
	int status = 0;
	String address;
	String customer;
	String sdtcustomer;
	int payment;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "idctv")
	Ctv ctv;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "idncc")
	Ncc ncc;

	@JsonManagedReference
	@OneToMany(mappedBy = "orders")
	List<Details> details;

}
