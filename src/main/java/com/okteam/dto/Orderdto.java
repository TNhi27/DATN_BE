package com.okteam.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Orderdto {
	Date dateorder;
	int total;
	int status=0;
	String address;
	String customer;
	String sdtcustomer;
	String payment;

    String idctv;
    String idncc;
}