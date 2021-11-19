package com.okteam.dao;

import java.util.Date;

import com.okteam.entity.Orders;
import com.okteam.entity.Report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o from Orders o where o.ctv.username=?1 and CAST(o.status AS string) LIKE ?2 ")
    public Page<Orders> getOrdersWithCtvStatus(String ctv, String status, Pageable pageable);

    @Query("SELECT o from Orders o where o.ctv.username=?1 and o.idorder =?2 ")
    public Page<Orders> getOrdersWithCtvId(String ctv, int id, Pageable pageable);

    // @Query("SELECT o from Orders o where o.ctv.username=?1 and o.status like ?1
    // and o.idorder=?2")
    // public Page<Orders> getOrdersWithCtv(String ctv, int status, int id, Pageable
    // pageable);

}