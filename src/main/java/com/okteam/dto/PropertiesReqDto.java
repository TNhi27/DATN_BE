package com.okteam.dto;

import com.okteam.entity.Properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesReqDto {
	int id;
    String keyp;
    String valuep;
    
    public PropertiesReqDto createByEntity(Properties pro) {
    	this.id = pro.getId();
    	this.keyp = pro.getKeyp();
    	this.valuep = pro.getValuep();
    	return this;
    }
}