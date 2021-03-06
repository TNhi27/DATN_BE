
package com.okteam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.okteam.entity.Products;
import com.okteam.entity.Rating;

public interface ProductRepository extends JpaRepository<Products, String> {
	@Query("Select o from Products o where o.idpro=?1 and o.active=?2")
	public Products findById(String id, Boolean active);

	@Query(value = "SELECT * FROM `datn`.`products` WHERE name LIKE ?1", nativeQuery = true)
	Page<Products> findByProducts(String name, Pageable pageble);

	@Query("Select o from Products o Where o.category.idcate=?1 and o.active=true")
	public List<Products> getProductsByCate(String category);

	@Query("Select o from Products o Where o.category.idcate=?1 and o.active=true")
	public Page<Products> getProductsByCate1(String category, Pageable pageable);

	@Query("SELECT o FROM Products o WHERE  o.category.idcate like ?1 and o.pricectv>= ?2 and o.pricectv<=?3 and o.origin in ?4 and o.ncc.city in (?5) and o.p_brand.id = ?6 and o.tags like %?7% and o.active=true")
	public Page<Products> getProductsByCategoryHasBrand(String category, Integer min, Integer max, List<String> origin,
			List<String> address_ncc, Integer brand, String q, Pageable pageable);

	@Query("SELECT o FROM Products o WHERE o.category.idcate like ?1 and o.pricectv>= ?2 and o.pricectv<=?3 and o.origin IN (?4) and o.ncc.city in (?5) and o.tags like ?6 and o.p_brand.id in (?7) and o.active=true")
	public Page<Products> getProductsByCategory(String category, Integer min, Integer max, List<String> origin,
			List<String> address_ncc, String q,List<Integer> brands, Pageable pageable);

	@Query("SELECT o FROM Products o WHERE  o.category.idcate in ?1 and o.pricectv>= ?2 and o.pricectv<=?3 and o.origin in ?4 and o.ncc.city in (?5) and o.tags like ?6 and o.p_brand.id in (?7) and o.active=true")
	public Page<Products> getProductsByParentCategory(List<String> category, Integer min, Integer max,
			List<String> origin, List<String> address_ncc, String q,List<Integer> brands, Pageable pageable);

	@Query("SELECT o FROM Products o WHERE o.ncc.username=?1 and o.active=true")
	public Page<Products> getProductWithNcc(String idncc, Pageable pageable);

	@Query("SELECT o FROM Products o WHERE o.ncc.username=?1 and o.category.idcate like ?2 and o.name like %?3% and o.active=true")
	public Page<Products> getProductWithNccV2(String idncc, String category, String name, Pageable pageable);

	@Query("SELECT new Rating(o.products.idpro,o.products.name,o.products.pricectv,o.products.origin,o.products.image0,avg(o.star)) FROM Comments o where o.products.active=true group by o.products.id ")
	public Page<Rating> getProductsWith5Star(Pageable pageable);

	@Query("SELECT o.origin FROM Products o group by o.origin")
	public List<String> getRootOrigin();

	@Query("SELECT o.ncc.city FROM Products o group by o.ncc.city")
	public List<String> getRootCityNcc();

	@Query("select o from Products o where o.active = ?1")
	public List<Products> findAll(Boolean active);
	
	@Query(value="SELECT * FROM products WHERE idncc=?1 ORDER BY createdate DESC", nativeQuery = true)
	public List<Products> findByIdNcc(String username);
}
