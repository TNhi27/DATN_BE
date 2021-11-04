package com.okteam.dao;

import com.okteam.entity.Orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    
}