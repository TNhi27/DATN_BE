package com.okteam.dto;

import com.okteam.entity.Category;

import lombok.Data;

@Data
public class Categorydto {
    String idcate;
	String typename;
	String img;
	Integer lv;
	String parent;
	
	public Categorydto createByEntity(Category c) {
		this.idcate = c.getIdcate();
		this.typename = c.getTypename();
		this.img = c.getImg();
		this.lv = c.getLv();
		this.parent= c.getParent();
		return this;
	}
	
}