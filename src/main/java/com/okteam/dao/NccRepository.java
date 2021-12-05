package com.okteam.dao;

import java.util.List;

import com.okteam.entity.Ncc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NccRepository extends JpaRepository<Ncc, String> {

    @Query("SELECT o.ncc FROM Products o where o.idpro=?1 ")
    public Ncc getNccByProduct(String id);

    @Query("SELECT o FROM Ncc o WHERE o.verify = ?1")
    public Ncc findByVerify(String verify);

    @Query("Select o from Ncc o where o.username=?1 and o.active=?2")
    public Ncc findById(String id, Boolean active);

    @Query("select MONTH(o.createdate) as month,COUNT(o) as total  from Ncc o where YEAR(o.createdate) = ?1 group by MONTH(o.createdate)")
    public List<Object> getReportCountUserInYear(int year);
}