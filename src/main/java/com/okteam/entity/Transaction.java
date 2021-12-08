package com.okteam.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idtran;
	int type;
	int value;
	String note;
	String username;
	Integer done;
	String idpaypal;

	@Temporal(TemporalType.TIMESTAMP)
	Date createdate;

	// @JsonIgnore
	// @ManyToOne
	// @JoinColumn(name = "idbank")
	// InfoBanks tran_bank;
}
