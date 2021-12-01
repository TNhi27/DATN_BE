package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okteam.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
