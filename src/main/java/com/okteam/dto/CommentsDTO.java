package com.okteam.dto;

import java.util.Date;

import com.okteam.entity.Ctv;
import com.okteam.entity.Products;

import lombok.Data;

@Data
public class CommentsDTO {
	int idcmt;
	int star;
	String content;
	Date createdate;
	String products;
	String username;
}
