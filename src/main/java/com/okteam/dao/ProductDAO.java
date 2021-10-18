package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okteam.entity.Products;

public interface ProductDAO extends JpaRepository<Products, String>{

}
