package com.okteam.dto;

import java.util.Date;
import java.util.List;

import com.okteam.entity.Comments;
import com.okteam.entity.Ctv;
import com.okteam.entity.FollowSell;
import com.okteam.entity.Orders;
import com.okteam.entity.RegiProducts;

import lombok.Data;

@Data
public class CtvReqDTO {
	String username;
	String email;
	String sdt;
	boolean active;
	String fullname;
	String address;
	String sex;
	String verify;
	Date createdate;
	String image;
	int money;

	public CtvReqDTO createByEntity(Ctv ctv) {
		username = ctv.getUsername();
		email = ctv.getEmail();
		sdt = ctv.getSdt();
		active = ctv.isActive();
		fullname = ctv.getFullname();
		address = ctv.getAddress();
		sex = ctv.getSex();
		verify = ctv.getVerify();
		createdate = ctv.getCreatedate();
		image = ctv.getImage();
		money = ctv.getMoney();

		return this;
	}

}
