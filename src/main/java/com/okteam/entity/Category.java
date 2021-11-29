package com.okteam.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.okteam.dto.Categorydto;

import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {
	@Id
	String idcate;
	String typename;
	String img;
	Integer lv;

	String parent;

	@JsonBackReference
	@OneToMany(mappedBy = "category")
	List<Products> products;

	@JsonBackReference
	@OneToMany(mappedBy = "br_category")
	List<Brand> brands;
	
	public Category dtoReturnEntity(Categorydto c) {
		this.idcate = c.getIdcate();
		this.typename = c.getTypename();
		this.img = c.getImg();
		this.lv = c.getLv();
		this.parent= c.getParent();
		return this;
	}
	
}
