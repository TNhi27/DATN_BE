package com.okteam.dto;

import java.util.Date;

import com.okteam.entity.InfoBanks;
import com.okteam.entity.Transaction;

import lombok.Data;

@Data
public class TransactionDTO {
	int idtran;
	int type;
	int value;
	String note;
	String username;
	int done;
	String idpaypal;
	Date createdate;
	InfoBanks tran_bank;
	
	public TransactionDTO createByEntity(Transaction tr) {
		this.idtran = tr.getIdtran();
		this.type = tr.getType();
		this.value = tr.getValue();
		this.note = tr.getNote();
		this.username = tr.getUsername();
		this.done = tr.getDone();
		this.idpaypal = tr.getIdpaypal();
		this.createdate = tr.getCreatedate();
//		this.tran_bank = tr.getTran_bank();
		return this;
	}
}
