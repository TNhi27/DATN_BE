package com.okteam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.okteam.entity.Products;

public interface ProductDAO extends JpaRepository<Products, String>{

    @Query("SELECT o FROM Products o WHERE o.ncc.idncc=?1")
    public Page<Products> getproductwithncc(Integer idncc, Pageable pageable);

}