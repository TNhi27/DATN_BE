package com.okteam.dto;

import java.util.Date;
import java.util.List;

import com.okteam.entity.Comments;
import com.okteam.entity.Details;
import com.okteam.entity.Products;
import com.okteam.entity.Properties;
import com.okteam.entity.RegiProducts;

import lombok.Data;

@Data
public class ProductsDTO {
	String idpro = null;
	String name;
	String description;
	int pricectv;
	boolean active;	
	Date createdate = new Date();
	int qty;
	String dvt;
	String image0;
	String image1;
	String image2;
	String image3;
	String origin;
	String tags;
	List<RegiProducts> list_regi;
	List<Comments> comments;
	List<Details> details;
	Categorydto category;
	NccResponseDTO ncc;
	List<Properties> properties;
	BrandDTO p_brand;
	
	public ProductsDTO createByEntity(Products p) {
		this.idpro = p.getIdpro();
		this.name = p.getName();
		this.description = p.getDescription();
		this.pricectv = p.getPricectv();
		this.active = p.isActive();
		this.createdate = p.getCreatedate();
		this.qty = p.getQty();
		this.dvt = p.getDvt();
		this.image0 = p.getImage0();
		this.image1 = p.getImage1();
		this.image2 = p.getImage2();
		this.image3 = p.getImage3();
		this.origin = p.getOrigin();
		this.tags = p.getTags();
		this.list_regi = p.getList_regi();
		this.comments = p.getComments();
		this.details = p.getDetails();
		this.category = new Categorydto().createByEntity(p.getCategory());
		this.ncc= new NccResponseDTO().createByEntity(p.getNcc());
		this.properties = p.getProperties();
		this.p_brand = new BrandDTO().createByEntity(p.getP_brand());
		return this;
	}
	
}
