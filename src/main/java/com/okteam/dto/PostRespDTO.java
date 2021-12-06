package com.okteam.dto;

import com.okteam.entity.Admin;
import com.okteam.entity.Post;

import lombok.Data;

@Data
public class PostRespDTO {
	int idpost;
    String title;
    String content;
    String image;
    Admin acc_post;
    
    public PostRespDTO createByEntity(Post po) {
    	this.idpost = po.getIdpost();
    	this.title = po.getTitle();
    	this.content = po.getContent();
    	this.image = po.getImage();
    	this.acc_post = po.getAcc_post();
    	return this;
    }
    
}
