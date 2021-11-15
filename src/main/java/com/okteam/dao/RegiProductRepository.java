package com.okteam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.RegiProducts;

public interface RegiProductRepository extends JpaRepository<RegiProducts, Integer> {

    @Query("select o from RegiProducts o where o.ctv.username=?1")
    public Page<RegiProducts> getRegiProductsWithNcc(String id, Pageable pageable);

}
