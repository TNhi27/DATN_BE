package com.okteam.dto;

import java.util.Date;
import lombok.Data;

@Data
public class Productdto {

	String idpro;
	String name;
	String description;
	int pricectv;
	boolean active=true;	
	Date createdate;
	
	int qty;
	String dvt;
	String image0;
	String image1;
	String image2;
	String image3;
	String origin;
	String tags;

	String idcate;
	String username;
	int idbrand;
}