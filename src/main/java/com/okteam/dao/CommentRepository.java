package com.okteam.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.okteam.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Integer> {

	
}
