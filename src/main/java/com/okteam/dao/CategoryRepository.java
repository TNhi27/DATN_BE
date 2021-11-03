package com.okteam.dao;

import com.okteam.entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
	List<Category> findByParent(String parent);
}
