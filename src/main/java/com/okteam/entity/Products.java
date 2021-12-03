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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.okteam.dto.Productdto;

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
	Date createdate = new Date();
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

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "idncc")
	Ncc ncc;

	@JsonBackReference
	@OneToMany(mappedBy = "p_properties")
	List<Properties> properties;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "brand")
	Brand p_brand;

	public Products dtoReturnEntity(Productdto p) {
		this.idpro = p.getIdpro();
		this.name = p.getName();
		this.description = p.getDescription();
		this.pricectv = p.getPricectv();
		this.active = p.isActive();
		this.qty = p.getQty();
		this.dvt = p.getDvt();
		this.image0 = p.getImage0();
		this.image1 = p.getImage1();
		this.image2 = p.getImage2();
		this.image3 = p.getImage3();
		this.origin = p.getOrigin();
		this.tags = p.getTags();
		return this;
	}
	
}
