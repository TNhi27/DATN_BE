package com.okteam.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="infobanks")
@Data
public class InfoBanks {
    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    int id;
    String bankname;
    String banksnumber;
    String username;

    @JsonManagedReference
    @ManyToOne @JoinColumn(name = "username")
    Accounts acc_banks;

    @JsonBackReference
    @OneToMany(mappedBy = "tran_bank")
    List<Transaction> transaction;
}