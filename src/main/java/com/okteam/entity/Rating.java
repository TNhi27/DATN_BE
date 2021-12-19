package com.okteam.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

	@Id
	String idpro;
	String name;
	int gia;
	String origin;
	String image;
	double rating = 0;
	
}
