package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okteam.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

}
