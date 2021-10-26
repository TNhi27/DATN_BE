package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.okteam.entity.Products;

public interface ProductDAO extends JpaRepository<Products, String>{
    @Query("Select o from Products o Where o.category.idcate=?1")
    public List<Products> getProductsByCate(String category);
}
