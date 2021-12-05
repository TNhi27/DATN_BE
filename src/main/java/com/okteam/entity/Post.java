package com.okteam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.okteam.dto.PostDTO;

import lombok.Data;

@Entity
@Table(name = "post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idpost;
    String title;
    String content;
    String image;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "username")
    Admin acc_post;
    
    public Post dtoReturnEntity(PostDTO po) {
    	this.title = po.getTitle();
    	this.content = po.getContent();
    	this.image = po.getImage();
    	return this;
    }
    
}