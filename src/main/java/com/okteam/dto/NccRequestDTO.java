package com.okteam.dto;

import java.util.Date;
import java.util.List;

import com.okteam.entity.Ncc;
import com.okteam.entity.Products;

import org.springframework.web.bind.annotation.PostMapping;

import lombok.Data;

@Data
public class NccRequestDTO {

	String username;
	String email;
	String sdt;

	String fullname;
	String address;
	String city;
	String sex;

	Date createdate;
	String nccname;
	String ncclogo;
	String description;
	int money = 0;
	int countFollow;
	int countProducts;
	int countOrders;

	public NccRequestDTO createByEntity(Ncc ncc) {

		this.address = ncc.getAddress();
		this.countFollow = ncc.getFollowSell() != null ? ncc.getFollowSell().size() : 0;
		this.createdate = ncc.getCreatedate();
		this.email = ncc.getEmail();
		this.fullname = ncc.getFullname();
		this.ncclogo = ncc.getNcclogo();
		this.nccname = ncc.getNccname();
		this.sdt = ncc.getSdt();
		this.sex = ncc.getSex();
		this.username = ncc.getUsername();

		this.countProducts = ncc.getProducts() != null ? ncc.getProducts().size() : 0;

		this.countOrders = ncc.getOrders() != null ? ncc.getOrders().size() : 0;
		this.city = ncc.getCity();
		this.description = ncc.getDescription();
		this.money = ncc.getMoney();
		return this;
	}

}
