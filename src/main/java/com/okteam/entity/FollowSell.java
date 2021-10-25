package com.okteam.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="followsell")
@Data
public class FollowSell {
    
    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    int followid;
    
    @Temporal(TemporalType.DATE)
    Date date;

    @JsonManagedReference
    @ManyToOne @JoinColumn(name = "ncc")
    Ncc fl_ncc;

    @JsonManagedReference
    @ManyToOne @JoinColumn(name = "ctv")
    Ctv fl_ctv;
}