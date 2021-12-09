package com.okteam.dto;

import java.util.Date;

import com.okteam.entity.Comments;
import com.okteam.entity.Ctv;
import com.okteam.entity.Products;

import lombok.Data;

@Data
public class CommentsRespDto {
	int idcmt;
	int star;
	String content;
	Integer idorder;
	Date createdate;
	Ctv ctv_cmt;
	Products products;
	
	public CommentsRespDto createByEntity(Comments cmt) {
		this.idcmt = cmt.getIdcmt();
		this.star = cmt.getStar();
		this.content = cmt.getContent();
		this.idorder = cmt.getIdorder();
		this.createdate = cmt.getCreatedate();
		this.ctv_cmt = cmt.getCtv_cmt();
		this.products = cmt.getProducts();
		return this;
	}
}
