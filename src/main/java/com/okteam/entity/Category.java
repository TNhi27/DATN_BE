package com.okteam.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name="category")
@Data
public class Category {
	@Id
	String idcate;
	String typename;
	String imgl;
	
	@JsonBackReference
	@OneToMany(mappedBy = "category")
	List<Products> products;
}
