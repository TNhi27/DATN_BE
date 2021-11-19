package com.okteam.dao;


import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

import com.okteam.entity.Orders;
import com.okteam.entity.ReportbyDay;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT new ReportbyDay(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public ReportbyDay getReportOrdersByDay1(int d, int m, int y);

    @Query("SELECT new ReportbyDay(MONTH(o.orders.dateorder),COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where YEAR(o.orders.dateorder)=?1 GROUP BY MONTH(o.orders.dateorder)")
    public List<ReportbyDay> getReportOrdersByMonth(int y);

    @Query("SELECT new ReportbyDay(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where o.orders.status=2 and DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public ReportbyDay getOrderDelayByDay(int d, int m, int y);

    @Query("SELECT new ReportbyDay(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where o.orders.status=3 and DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public ReportbyDay getOrderWaitingByDay(int d, int m, int y);

    @Query("SELECT new ReportbyDay(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where o.orders.status=4 and DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public ReportbyDay getShipping(int d, int m, int y);

    @Query("SELECT new ReportbyDay(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where o.orders.status=0 and DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public ReportbyDay getCancelByDate(int d, int m, int y);

    

}