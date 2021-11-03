package com.okteam.dao;

import com.okteam.entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
<<<<<<< HEAD

	
=======
	List<Category> findByParent(String parent);
>>>>>>> 6c1e618fb3e2acbc76f3c8273d9de874ed1079b0
}
