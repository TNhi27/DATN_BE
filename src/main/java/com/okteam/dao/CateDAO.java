package com.okteam.dao;

import com.okteam.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CateDAO extends JpaRepository<Category,String> {
    
}
