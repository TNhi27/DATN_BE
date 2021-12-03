package com.okteam.dto;

import java.util.Date;
import java.util.List;

import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;

import lombok.Data;

@Data
public class OrdersRequestDTO {
    int id;
    Date dateorder;
    String address;
    String customer;
    String sdtcustomer;
    String payment;
    String order_code;
    int total;
	int status;
    Ctv ctv;
	Ncc ncc;
    List<DetailsDTO> details;
}