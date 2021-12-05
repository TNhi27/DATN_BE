package com.okteam.dto;

import java.util.List;
import java.util.Optional;

import com.okteam.entity.InfoBanks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InfoBanksRepository extends JpaRepository<InfoBanks, Integer> {

    @Query("select o from InfoBanks o where o.username = ?1")
    public Optional<InfoBanks> selectByUsername(String username);

}