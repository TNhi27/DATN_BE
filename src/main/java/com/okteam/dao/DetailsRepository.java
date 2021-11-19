package com.okteam.dao;

import java.util.List;

import com.okteam.entity.Details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DetailsRepository extends JpaRepository<Details, Integer>{
}