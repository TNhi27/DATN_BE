package com.okteam.dto;

import com.okteam.entity.Brand;

import lombok.Data;

@Data
public class BrandDTO {
	int id;
    String name;
    
    public BrandDTO createByEntity(Brand b) {
    	this.id = b.getId();
    	this.name = b.getName();
    	return this;
    }
    
}
