package com.okteam.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionDTO {
	int idtran;
	
	int type;
	String username;
	String value;
	String note;
	boolean done;
	Date createdate;
	
	int id;
}
