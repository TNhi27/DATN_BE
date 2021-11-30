package com.okteam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

	@Query(value = "SELECT * FROM brand  WHERE idcate=?1", nativeQuery = true)
	List<Brand> findByIdcate(String idcate);
}
