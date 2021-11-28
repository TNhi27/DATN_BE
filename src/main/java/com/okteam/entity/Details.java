package com.okteam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "details")
@Data
public class Details {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int iddetails;
	int qty;
	Integer revenue;

	@ManyToOne
	@JoinColumn(name = "idpro")
	@JsonManagedReference
	Products products;

	@ManyToOne
	@JoinColumn(name = "idorder")
	@JsonBackReference
	Orders orders;

}
