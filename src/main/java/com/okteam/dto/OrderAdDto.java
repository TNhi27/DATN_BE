package com.okteam.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderAdDto {
	int idorder;
	int total;
	int status;
	String address;
	String customer;
	String sdtcustomer;
	int payment;
	String order_code;
	String huyen;
	String xa;
	String ctv;
	String ncc;
	List<DetailsDTO> details;
}
