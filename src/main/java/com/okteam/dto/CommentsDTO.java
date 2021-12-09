package com.okteam.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentsDTO {
	int idcmt;
	int star;
	String content;
	Date createdate;
	String products;
	String username;
	Integer idorder;
}
