package com.okteam.dto;

import java.util.Date;
import java.util.List;

import com.okteam.entity.Ctv;
import com.okteam.entity.Details;
import com.okteam.entity.Ncc;
import com.okteam.entity.Orders;

import lombok.Data;

@Data
public class OrdersResponseDTO {
	int idorder;
	Date dateorder;
	Date datefinish;
	int total;
	int status = 0;
	String address;
	String customer;
	String sdtcustomer;
	int payment;
	String order_code;
	String huyen;
	String xa;

	Ctv ctv;
	Ncc ncc;
	List<Details> details;
	
	public OrdersResponseDTO createByEntity(Orders o) {
		this.idorder = o.getIdorder();
		this.dateorder = o.getDateorder();
		this.datefinish = o.getDatefinish();
		this.total = o.getTotal();
		this.status = o.getStatus();
		this.address = o.getAddress();
		this.customer = o.getCustomer();
		this.sdtcustomer = o.getSdtcustomer();
		this.payment = o.getPayment();
		this.order_code = o.getOrder_code();
		this.huyen = o.getHuyen();
		this.xa = o.getXa();
		this.ctv = o.getCtv();
		this.ncc = o.getNcc();
		this.details = o.getDetails();
		return this;
	}
	
}
