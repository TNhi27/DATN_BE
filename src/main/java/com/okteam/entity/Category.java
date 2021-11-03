package com.okteam.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonManagedReference;
=======
>>>>>>> 6c1e618fb3e2acbc76f3c8273d9de874ed1079b0

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
>>>>>>> 6c1e618fb3e2acbc76f3c8273d9de874ed1079b0
	String parent;


	
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	List<Products> products;

	@JsonIgnore
	@OneToMany(mappedBy = "br_category")
	List<Brand> brands;
}
