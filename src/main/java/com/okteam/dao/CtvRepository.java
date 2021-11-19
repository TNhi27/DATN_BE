package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.Ctv;


public interface CtvRepository extends JpaRepository<Ctv, String>{
	@Query("SELECT o FROM Ctv o WHERE o.verify = ?1")
	public Ctv findByVerify(String verify);
}
