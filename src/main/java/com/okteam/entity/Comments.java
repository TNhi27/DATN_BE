package com.okteam.entity;

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

import lombok.Data;

@Entity
@Data
@Table(name="comments")
public class Comments {
	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	int idcmt;
	int star;
	String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createdate;

	@JsonManagedReference
	@ManyToOne @JoinColumn(name = "username")
	Ctv ctv_cmt;
	
	@JsonManagedReference
	@ManyToOne @JoinColumn(name = "idpro")
	Products products;
}
