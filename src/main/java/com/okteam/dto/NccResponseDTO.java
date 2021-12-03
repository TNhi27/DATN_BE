package com.okteam.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	String city;
	String sex;
	String verify;
	Date createdate;
	String nccname;
	String ncclogo;
	String description;
	String idghn;
	int money = 0;
	int countFollow;
	int countProducts;
	int countOrders;
	List<Products> products;

	public NccResponseDTO createByEntity(Ncc ncc) {
		this.active = ncc.isActive();
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
		this.verify = ncc.getVerify();
		this.countProducts = ncc.getProducts() != null
				? ncc.getProducts().stream().filter((e) -> e.isActive()).collect(Collectors.toList()).size()
				: 0;
		this.products = ncc.getProducts();
		this.countOrders = ncc.getOrders() != null ? ncc.getOrders().size() : 0;
		this.city = ncc.getCity();
		this.description = ncc.getDescription();
		this.idghn = ncc.getIdghn();
		this.money = ncc.getMoney();
		return this;
	}
}
