package com.okteam.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.okteam.dto.BrandDTO;

import lombok.Data;

@Entity
@Table(name = "brand")
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "idcate")
    Category br_category;

    @JsonBackReference
    @OneToMany(mappedBy = "p_brand")
    List<Products> products;
    
    public Brand dtoReturnEntity(BrandDTO b) {
    	this.id = b.getId();
    	this.name = b.getName();
    	return this;
    }
    
}