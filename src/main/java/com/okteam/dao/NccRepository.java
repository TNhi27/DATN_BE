package com.okteam.dao;

import java.util.List;

import com.okteam.dto.NccResponseDTO;
import com.okteam.entity.Ncc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NccRepository extends JpaRepository<Ncc, String> {

    @Query("SELECT o.ncc FROM Products o where o.idpro=?1 ")
    public Ncc getNccByProduct(String id);
}