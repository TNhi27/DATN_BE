package com.okteam.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrdersRequestDTO {
    int id;
    Date dateorder;

    String address;
    String customer;
    String sdtcustomer;
    String payment;

    List<DetailsDTO> details;
}