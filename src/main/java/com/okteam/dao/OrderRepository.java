package com.okteam.dao;

import java.util.Date;

import com.okteam.entity.Orders;
import com.okteam.entity.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT new Report(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public Report getget(int day, int m, int y);

}