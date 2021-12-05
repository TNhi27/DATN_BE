package com.okteam.dto;

import lombok.Data;

@Data
public class PostDTO {
	int idpost;
	String title;
    String content;
    String image;
    String username;
}
