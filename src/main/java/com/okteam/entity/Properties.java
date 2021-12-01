package com.okteam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.okteam.dto.PropertiesReqDto;

import lombok.Data;

@Entity
@Table(name = "properties")
@Data

public class Properties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "idpro")
    Products p_properties;

    String valuep;
    String keyp;
    
    public Properties dtoReturnEntity(PropertiesReqDto pro) {
    	this.id = pro.getId();
    	this.keyp = pro.getKeyp();
    	this.valuep = pro.getValuep();
    	return this;
    }
    
}