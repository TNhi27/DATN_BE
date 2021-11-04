package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okteam.entity.RegiProducts;


public interface RegiProductRepository extends JpaRepository<RegiProducts, Integer>{
	
}
