package com.okteam.dto;

import java.util.List;

import com.okteam.entity.Ctv;
import com.okteam.entity.Ncc;
import com.okteam.entity.Orders;
import com.okteam.entity.Products;
import com.okteam.entity.ReportbyDay;

import lombok.Data;

@Data
public class ReportResponseNcc {
    Long total_d;
    Long total_m;
    Long count_products_d;
    Long count_order_d;
    Long count_order0;
    Long count_order1;
    Long count_order2;
    Long count_order3;
    Long count_order4;
    int count_orders;

    int money;
    List<Ctv> list_ctv;
    List<Ctv> list_ctv_d;
    List<Orders> list_orders;
    List<ReportbyDay> list_report;
    List<Products> list_product;
}