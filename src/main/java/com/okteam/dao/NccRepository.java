package com.okteam.dao;
<<<<<<< HEAD
import java.util.List;

import com.okteam.entity.Ncc;
import com.okteam.entity.Products;
=======

import java.util.List;

import com.okteam.dto.NccResponseDTO;
import com.okteam.entity.Ncc;
>>>>>>> b3ee7eb10314c1a3d004a8a5c247d5924847da9e

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

<<<<<<< HEAD
public interface NccRepository extends JpaRepository<Ncc, Integer> {
    @Query(value = "SELECT * FROM Ncc ORDER BY RAND() LIMIT 1", nativeQuery = true)
    public List<Ncc> get10ncc();
    
=======
public interface NccRepository extends JpaRepository<Ncc, String> {

    @Query("SELECT o.ncc FROM Products o where o.idpro=?1 ")
    public Ncc getNccByProduct(String id);
>>>>>>> b3ee7eb10314c1a3d004a8a5c247d5924847da9e
}