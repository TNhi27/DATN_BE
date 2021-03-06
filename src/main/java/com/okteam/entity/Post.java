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
    @Temporal(TemporalType.DATE)
    Date createdate = new Date();
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "username")
    Admin acc_post;

    public Post dtoReturnEntity(PostDTO po) {
        this.idpost = po.getIdpost();
        this.title = po.getTitle();
        this.content = po.getContent();
        this.image = po.getImage();
        return this;
    }

}