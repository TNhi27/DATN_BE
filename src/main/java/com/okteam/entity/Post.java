package com.okteam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="post")
@Data
public class Post {
    
    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    int id;
    String title;
    String content;
    String image;
    String username;

    @JsonManagedReference
    @ManyToOne @JoinColumn(name = "username")
    Accounts acc_post;
}