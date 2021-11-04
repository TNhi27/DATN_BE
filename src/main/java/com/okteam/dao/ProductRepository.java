
package com.okteam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.okteam.entity.Products;
import com.okteam.entity.Rating;

public interface ProductRepository extends JpaRepository<Products, String> {

	@Query(value = "SELECT * FROM `datn`.`products` WHERE name LIKE ?1", nativeQuery = true)
	Page<Products> findByProducts(String name, Pageable pageble);

	@Query("Select o from Products o Where o.category.idcate=?1")
	public List<Products> getProductsByCate(String category);


	@Query("SELECT o FROM Products o WHERE o.category.idcate=?1")
	public Page<Products> getProductsByCategory(String category, Pageable pageable);


	@Query("SELECT o FROM Products o WHERE o.ncc.username=?1")
	public Page<Products> getProductWithNcc(String idncc, Pageable pageable);
	
	@Query("SELECT new Rating(o.products.idpro,o.products.name,o.products.pricectv,o.products.origin,o.products.image0,avg(o.star)) FROM Comments o group by o.products.id")
	public Page<Rating> getProductsWith5Star(Pageable pageable);
		
	

}
