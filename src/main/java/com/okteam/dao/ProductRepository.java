
package com.okteam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.Products;

public interface ProductRepository extends JpaRepository<Products, String> {
	
	@Query(value = "SELECT * FROM `datn`.`products` WHERE name LIKE ?1",nativeQuery = true)
	Page<Products> findByProducts(String name, Pageable pageble);

}
