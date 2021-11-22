package com.okteam.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Orderdto {

	int status;
	String address;
	String customer;
	String sdtcustomer;
	int payment;
	int total;

	String idncc;

}