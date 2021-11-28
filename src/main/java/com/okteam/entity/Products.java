package com.okteam.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Products {
	@Id
	String idpro;
	String name;
	String description;
	int pricectv;
	boolean active = true;
	@Temporal(TemporalType.DATE)
	Date createdate;

	int qty;
	String dvt;
	String image0;
	String image1;
	String image2;
	String image3;
	String origin;
	String tags;

	@JsonBackReference
	@OneToMany(mappedBy = "products")
	List<RegiProducts> list_regi;

	@JsonBackReference
	@OneToMany(mappedBy = "products")
	List<Comments> comments;

	@JsonBackReference
	@OneToMany(mappedBy = "products")
	List<Details> details;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "idcate")
	Category category;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idncc")
	Ncc ncc;

	@JsonManagedReference
	@OneToMany(mappedBy = "p_properties")
	List<Properties> properties;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "brand")
	Brand p_brand;

}
