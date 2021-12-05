package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.okteam.entity.Ctv;

public interface CtvRepository extends JpaRepository<Ctv, String> {
	@Query("SELECT o FROM Ctv o WHERE o.verify = ?1")
	public Ctv findByVerify(String verify);

	@Query("Select o from Ctv o where o.username=?1 and o.active=?2")
	public Ctv findById(String id, Boolean active);

	@Query("select MONTH(o.createdate),COUNT(o)  from Ctv o where YEAR(o.createdate) = ?1 group by MONTH(o.createdate)")
	public List<Object> getReportCountUserInYear(int year);
}
