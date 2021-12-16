package com.okteam.dao;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.okteam.entity.Comments;
import com.okteam.entity.Orders;
import com.okteam.entity.ProductGroup;
import com.okteam.entity.ReportMonth;
import com.okteam.entity.ReportbyDay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o from Orders o where o.ctv.username=?1 and CAST(o.status AS string) LIKE ?2 ")
    public Page<Orders> getOrdersWithCtvStatus(String ctv, String status, Pageable pageable);

    @Query("SELECT o from Orders o where o.ctv.username=?1 and o.idorder =?2 ")
    public Page<Orders> getOrdersWithCtvId(String ctv, int id, Pageable pageable);

    @Query("SELECT o from Orders o where o.ncc.username=?1 and CAST(o.status AS string) LIKE ?2 ")
    public Page<Orders> getOrdersWithNccStatus(String ncc, String status, Pageable pageable);

    @Query("SELECT o from Orders o where o.ncc.username=?1 and o.idorder =?2 ")
    public Page<Orders> getOrdersWithNccId(String ncc, int id, Pageable pageable);

    @Query("SELECT count(o.orders.total) from Details o where  o.orders.ctv =?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public Long getDoanhthuThang(String ctv, int m, int y);

    @Query("SELECT count(o.orders.total) from Details o where  o.orders.ncc =?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public Long getDoanhthuThangNcc(String ncc, int m, int y);

    @Query("SELECT new ReportbyDay(1,COUNT(distinct(o.orders)),SUM(o.orders.total),SUM(o.qty)  ) from Details o where DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 and o.orders.ctv.username=?4 and o.orders.status in (0,1,2,5)")
    public ReportbyDay getReportOrdersByDay1(int d, int m, int y, String ctv);

    @Query("SELECT new ReportbyDay(1,COUNT(distinct(o.orders)),SUM(o.orders.total),SUM(o.qty)  ) from Details o where DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 and o.orders.ncc.username=?4 and o.orders.status in (0,1,2,5)")
    public ReportbyDay getReportOrdersByDay2(int d, int m, int y, String ncc);

    @Query("SELECT new ReportbyDay(MONTH(o.orders.dateorder),COUNT(distinct(o.orders)),SUM(o.orders.total),SUM(o.qty)  ) from Details o where YEAR(o.orders.dateorder)=?1 and o.orders.ctv.username=?2 GROUP BY MONTH(o.orders.dateorder) ")
    public List<ReportbyDay> getReportOrdersByMonth(int y, String ctv);

    @Query("SELECT new ReportbyDay(MONTH(o.orders.dateorder),COUNT(distinct(o.orders)),SUM(o.orders.total),SUM(o.qty)  ) from Details o where YEAR(o.orders.dateorder)=?1 and o.orders.ncc.username=?2 GROUP BY MONTH(o.orders.dateorder) ")
    public List<ReportbyDay> getReportOrdersByMonthNcc(int y, String ncc);

    @Query("SELECT new ReportbyDay(o.orders.status,COUNT(DISTINCT(o.orders)),SUM(o.orders.total),SUM(o.qty)  ) from Details o where  DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 and o.orders.ctv.username=?4 group by o.orders.status")
    public List<ReportbyDay> getOrderWithStatus(int d, int m, int y, String ctv);

    @Query("SELECT new ReportbyDay(o.orders.status,COUNT(DISTINCT(o.orders)),SUM(o.orders.total),SUM(o.qty)  ) from Details o where  DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 and o.orders.ncc.username=?4 group by o.orders.status")
    public List<ReportbyDay> getOrderWithStatusNcc(int d, int m, int y, String ncc);

    @Query("SELECT new ReportbyDay(o.orders.status,COUNT(DISTINCT(o.orders)),SUM(o.orders.total),SUM(o.qty)  ) from Details o where  DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 group by o.orders.status")
    public List<ReportbyDay> getOrderWithStatusAdmin(int d, int m, int y);

    @Query("SELECT new ReportbyDay(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where o.orders.status=3 and DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public ReportbyDay getOrderWaitingByDay(int d, int m, int y);

    @Query("SELECT new ReportbyDay(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where o.orders.status=4 and DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public ReportbyDay getShipping(int d, int m, int y);

    @Query("SELECT new ReportbyDay(1,COUNT(o.orders.idorder),SUM(o.orders.total),SUM(o.qty)  ) from Details o where o.orders.status=0 and DAY(o.orders.dateorder)=?1 and MONTH(o.orders.dateorder) =?2 and YEAR(o.orders.dateorder)=?3 ")
    public ReportbyDay getCancelByDate(int d, int m, int y);

    @Query("SELECT new ProductGroup(o.products.idpro,o.products.name,count(o.qty)) from Details o where o.orders.ncc.username =?1 group by o.products ")
    public List<ProductGroup> getProductGroups(String ncc);

    @Query("SELECT new ProductGroup(o.products.idpro,o.products.name,count(o.qty)) from Details o group by o.products ")
    public List<ProductGroup> getProductGroupsAdmin();
    @Query("SELECT new ReportMonth(year(o.dateorder), sum(o.payment)) "
			+ " FROM Orders o WHERE o.status = 5 AND month(o.dateorder)=?1 AND year(o.dateorder)=?2"
			+ " GROUP BY year(o.dateorder)"
			+ " ORDER BY year(o.dateorder) DESC")
    public ReportMonth getReportMonth(int thang, int nam);
    @Query("SELECT year(o.dateorder) FROM Orders o WHERE o.status = 5"
    		+ " GROUP BY year(o.dateorder)"
			+ " ORDER BY year(o.dateorder) DESC")
    public List<Integer> getYears();


   
        
    

}