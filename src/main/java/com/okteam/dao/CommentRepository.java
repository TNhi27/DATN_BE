package com.okteam.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Integer> {

	
	@Query("select o from Comments o where o.products.idpro=?1")
	public Page<Comments> getCommentsOfProduct(String idpro,Pageable pageable);
		
	
}
