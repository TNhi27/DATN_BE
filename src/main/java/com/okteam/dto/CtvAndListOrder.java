package com.okteam.dto;

import java.util.List;

import com.okteam.entity.Ctv;
import com.okteam.entity.Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CtvAndListOrder {
    Ctv ctv;
    List<Orders> orders_done;
}