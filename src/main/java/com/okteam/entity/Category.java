package com.okteam.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.Data;

@Entity
@Table(name="category")
@Data
public class Category {
	@Id
	String idcate;
	String typename;
	String img;
<<<<<<< HEAD
=======

	String parent;


>>>>>>> b3ee7eb10314c1a3d004a8a5c247d5924847da9e
	
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	List<Products> products;
<<<<<<< HEAD
}
=======

	@JsonIgnore
	@OneToMany(mappedBy = "br_category")
	List<Brand> brands;
}
>>>>>>> b3ee7eb10314c1a3d004a8a5c247d5924847da9e
