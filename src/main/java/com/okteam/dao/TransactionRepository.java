package com.okteam.dao;

import java.util.List;

import com.okteam.entity.GiaoDichGroup;
import com.okteam.entity.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("select o from Transaction o where CAST(o.type AS string) like ?1  and o.username = ?2")
    public Page<Transaction> getTransaction(String type, String username, Pageable pageable);

    @Query("SELECT new GiaoDichGroup(MONTH(o.createdate),o.type,COUNT(o),SUM(o.value)) from Transaction o group by MONTH(o.createdate),o.type ")
    public List<GiaoDichGroup> getGiaoDichGroup();
}