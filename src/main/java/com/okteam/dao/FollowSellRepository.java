package com.okteam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.FollowSell;
import com.okteam.entity.Ncc;
import com.okteam.entity.ReportFollow;

public interface FollowSellRepository extends JpaRepository<FollowSell, Integer> {

	@Query("SELECT COUNT(o) from FollowSell o where o.fl_ncc.username = ?1")
	public int countFollow(String username_ncc);

	@Query("SELECT new ReportFollow(1,COUNT(o.fl_ctv) ) from FollowSell o where DAY(o.date)=?1 and MONTH(o.date)=?2 and YEAR(o.date)=?3 ")
	public ReportFollow getReportFollow(int d, int m, int y);

	@Query("SELECT o from FollowSell o where o.fl_ctv.username = ?1")
	public Page<FollowSell> getNccFollow(String ctv, Pageable pageable);

	@Query("SELECT o from FollowSell o where o.fl_ncc.username = ?1")
	public Page<FollowSell> getCtvFollow(String ncc, Pageable pageable);

	@Query("SELECT o from FollowSell o where o.fl_ctv.username = ?1 and o.fl_ncc.username=?2")
	public FollowSell getFollow(String ctv, String ncc);

}
