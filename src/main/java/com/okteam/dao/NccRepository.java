package com.okteam.dao;

import com.okteam.entity.Ncc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NccRepository extends JpaRepository<Ncc, String> {

    @Query("SELECT o.ncc FROM Products o where o.idpro=?1 ")
    public Ncc getNccByProduct(String id);

    @Query("SELECT o FROM Ncc o WHERE o.verify = ?1")
    public Ncc findByVerify(String verify);

}