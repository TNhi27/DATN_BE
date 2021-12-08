package com.okteam.dao;

import com.okteam.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsReponsitory extends JpaRepository<Post, Integer> {

}