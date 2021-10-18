
package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okteam.entity.Products;

public interface ProductRepository extends JpaRepository<Products, String> {
	
}
