package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okteam.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{
	
}
