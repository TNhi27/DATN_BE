package com.okteam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.okteam.entity.Accounts;

public interface AccountRepository extends JpaRepository<Accounts, String>{

}
