package com.okteam.entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@Table(name = "Regi_products")
public class RegiProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idregi;
	int price;

	@Temporal(TemporalType.TIMESTAMP)
	Date regidate;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "idpro")
	Products products;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "idctv")
	Ctv ctv;
}
