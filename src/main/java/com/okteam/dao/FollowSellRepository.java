package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.FollowSell;

public interface FollowSellRepository extends JpaRepository<FollowSell, Integer>{

	@Query("SELECT COUNT(o) from FollowSell o where o.fl_ncc.username = ?1")
	public int countFollow(String username_ncc);
		
	
}
