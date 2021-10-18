package com.okteam.dao;
import com.okteam.entity.Ncc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NccRepository extends JpaRepository<Ncc, Integer> {
    
}