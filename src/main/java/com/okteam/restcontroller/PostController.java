package com.okteam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.PostRepository;
import com.okteam.dto.PostRespDTO;
import com.okteam.entity.Response;
import com.okteam.utils.DtoUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/post")
public class PostController {
	@Autowired
	PostRepository poRepo;
	@Autowired
	DtoUtils dtoUtils;
	
	@GetMapping("/list")
	public Response<PostRespDTO> getList(){
		return new Response<PostRespDTO>(dtoUtils.mapPostToDto(poRepo.findAll()), null, "OK");
	}
}
