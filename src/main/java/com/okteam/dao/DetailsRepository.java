package com.okteam.dao;

import com.okteam.entity.Details;
import com.okteam.entity.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, Integer> {
    List<Details>findByOrdersEquals(Orders orders);
}