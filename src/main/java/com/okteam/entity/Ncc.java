package com.okteam.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
<<<<<<< HEAD
import com.okteam.dto.NccDto;

=======
import com.fasterxml.jackson.annotation.JsonIgnore;
>>>>>>> c5bd9ff939c110c165cd4d3d959ed66a45b16877

import lombok.Data;

@Entity
@Table(name = "ncc")
@Data
public class Ncc {
	@Id
	String username;
	@JsonIgnore
	String password;
	String email;
	String sdt;
	boolean active = true;
	String fullname = "Ẩn danh";
	String address;
	String city;
	String sex = "Khác";
	String verify = null;
	@Temporal(TemporalType.DATE)
	Date createdate = new Date();
	String nccname = "Ẩn danh";
	String ncclogo;
	String description = "Xin chào";
	String idghn;

	int money = 0;

	@JsonBackReference
	@OneToMany(mappedBy = "ncc")
	List<Products> products;

	@JsonBackReference
	@OneToMany(mappedBy = "ncc")
	List<Orders> orders;

	@JsonBackReference
	@OneToMany(mappedBy = "fl_ncc")
	List<FollowSell> followSell;

	public Ncc dtoReturnEntity(NccDto ncc) {
		Ncc n = new Ncc();
		n.setActive(ncc.isActive());
		n.setPassword(ncc.getPassword());
		n.setAddress(ncc.getAddress());
		n.setEmail(ncc.getEmail());
		n.setFullname(ncc.getNccname());
		n.setNcclogo(ncc.getNcclogo());
		n.setNccname(ncc.getNccname());
		n.setSdt(ncc.getSdt());
		n.setUsername(ncc.getUsername());
		n.setCity(ncc.getCity());
		n.setDescription(ncc.getDescription());
		n.setIdghn(ncc.getIdghn());
		return n;
	}
	
}
