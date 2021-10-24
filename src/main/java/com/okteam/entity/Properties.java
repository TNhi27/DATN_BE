package com.okteam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name="properties")
@Data
public class Properties {
    
    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    int id;
    String key;
    String value;
   

    @JsonBackReference
    @ManyToOne @JoinColumn(name = "idpro")
    Products p_properties;
}