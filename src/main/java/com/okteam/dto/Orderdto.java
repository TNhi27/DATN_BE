package com.okteam.dto;

import java.util.Date;


import lombok.Data;

@Data
public class Orderdto {

	Date dateorder;

	int status = 0;
	String address;
	String customer;
	String sdtcustomer;
	int payment;

	String idncc;

}