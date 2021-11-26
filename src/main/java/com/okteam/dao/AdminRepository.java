package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.okteam.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
    @Query("Select o from Admin o where o.username=?1 and o.active=?2")
    public Admin findById(String id, Boolean active);

}
