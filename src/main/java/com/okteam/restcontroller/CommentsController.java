package com.okteam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CommentRepository;

@RestController
@RequestMapping("/api/v1/comments")
@CrossOrigin
public class CommentsController {

	@Autowired
	CommentRepository commentRepository;
	
	public void name() {
		
	}
	
}
