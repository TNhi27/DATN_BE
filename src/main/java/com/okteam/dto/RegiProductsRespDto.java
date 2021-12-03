package com.okteam.dto;

import java.util.Date;

import com.okteam.entity.Ctv;
import com.okteam.entity.Products;
import com.okteam.entity.RegiProducts;

import lombok.Data;

@Data
public class RegiProductsRespDto {
	int idregi;
	int price;
	Date regidate;
	Products products;
	Ctv ctv;
	
	public RegiProductsRespDto createByEntity(RegiProducts regi) {
		this.idregi = regi.getIdregi();
		this.price = regi.getPrice();
		this.regidate = regi.getRegidate();
		this.products = regi.getProducts();
		this.ctv = regi.getCtv();
		return this;
	}
	
}
