package com.okteam.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.okteam.entity.Ncc;
import com.okteam.entity.Products;

import lombok.Data;

@Data
public class NccResponseDTO {

	String username;
	String email;
	String sdt;
	boolean active;
	String fullname;
	String address;
	String sex;
	String veryfy;
	String city;
	Date createdate;
	String nccname;
	String ncclogo;
	int countFollow;
	int countProducts;
	int countOrders;
	List<Products> products;

	public void createByEntity(Ncc ncc) {
		this.active = ncc.isActive();
		this.address = ncc.getAddress();
		this.countFollow = ncc.getFollowSell().size();
		this.createdate = ncc.getCreatedate();
		this.email = ncc.getEmail();
		this.fullname = ncc.getFullname();
		this.ncclogo = ncc.getNcclogo();
		this.nccname = ncc.getNccname();
		this.sdt = ncc.getSdt();
		this.sex = ncc.getSex();
		this.username = ncc.getUsername();
		this.veryfy = ncc.getVeryfy();
		this.countProducts = ncc.getProducts().size();
		this.products = ncc.getProducts();
		this.countOrders = ncc.getOrders().size();
		this.city = ncc.getCity();
	}
}
