package com.okteam.dto;

import java.util.List;

import com.okteam.entity.GiaoDichGroup;
import com.okteam.entity.ProductGroup;

import lombok.Data;
import lombok.extern.java.Log;

@Data
public class ReportAdmin {
    // người dùng
    Long total_ctv = (long) 0;
    Long total_ncc = (long) 0;
    List<Object> rp_ctv;
    List<Object> rp_ncc; // chứa đói tượng mảng : index=0 là tháng, index=1 là số người đằng ký

    // Sản phẩm
    Long total_sp = (long) 0;
    List<ProductGroup> rp_products; // sản phẩm bán được

    // đơn hàng
    Long total_order = (long) 0;
    Long count_order0 = (long) 0;
    Long count_order1 = (long) 0;
    Long count_order2 = (long) 0;
    Long count_order3 = (long) 0;
    Long count_order4 = (long) 0;
    Long count_order5 = (long) 0;

    // giao dịch
    List<GiaoDichGroup> rp_gd;

}