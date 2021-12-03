package com.okteam.dao;

import com.okteam.entity.Properties;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PropertiesReponsitory extends JpaRepository<Properties, Integer> {
	@Query(value = "SELECT * FROM properties  WHERE idpro=?1", nativeQuery = true)
	List<Properties> findByIdpro(String idpro);
}