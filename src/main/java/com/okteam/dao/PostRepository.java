package com.okteam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.okteam.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT o from Post o where o.title like ?1")
    public Page<Post> selectByTitle(String title, Pageable pageable);

}
