package com.okteam.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

	@Query(value = "SELECT * FROM brand  WHERE idcate=?1", nativeQuery = true)
	List<Brand> findByIdcate(String idcate);

	@Query(value = "SELECT o FROM Brand o WHERE o.br_category.idcate like ?1")
	Page<Brand> findByCategory(String idcate,Pageable pageable);
}
