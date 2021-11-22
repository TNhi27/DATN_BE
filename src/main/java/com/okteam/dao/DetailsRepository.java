package com.okteam.dao;



import com.okteam.entity.Details;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, Integer> {
    

}