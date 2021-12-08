package com.okteam.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.okteam.dto.OrderAdDto;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idorder;
	@Temporal(TemporalType.TIMESTAMP)
	Date dateorder = new Date();
	@Temporal(TemporalType.DATE)
	Date datefinish;
	int total;
	int status = 0;
	String address;
	String customer;
	String sdtcustomer;
	int payment;
	Integer total_fee; // phi van chuyen
	// String xa;
	// String huyen;
	// String tinh;
	String order_code;
	String tinh;
	String huyen;
	String xa;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "idctv")
	Ctv ctv;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "idncc")
	Ncc ncc;

	@JsonManagedReference
	@OneToMany(mappedBy = "orders")
	List<Details> details;

	public Orders dtoReturnEntity(OrderAdDto ord) {
		this.idorder = ord.getIdorder();
		this.total = ord.getTotal();
		this.status = ord.getStatus();
		this.address = ord.getAddress();
		this.customer = ord.getCustomer();
		this.sdtcustomer = ord.getSdtcustomer();
		this.payment = ord.getPayment();
		this.order_code = ord.getOrder_code();
		this.tinh = ord.getTinh();
		this.huyen = ord.getHuyen();
		this.xa = ord.getXa();
		return this;
	}

}
