package com.okteam.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrdersRequest {
    int status;
    String address;
    String customer;
    String sdtcustomer;
    int payment;
    int total;
    String xa;
    String huyen;
    String tinh;
    String idncc;

    List<RegiProductsDTO> details;
}