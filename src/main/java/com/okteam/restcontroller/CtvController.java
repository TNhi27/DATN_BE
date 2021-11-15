package com.okteam.restcontroller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CtvRepository;
import com.okteam.entity.Ctv;
import com.okteam.entity.Response;
import com.okteam.utils.RegisterService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/ctv")
public class CtvController {
	
	@Autowired
	RegisterService service;
	@Autowired
	CtvRepository repo;

	@PostMapping("/register")
	public Response<Ctv> register(@RequestBody Ctv ctv) throws UnsupportedEncodingException, MessagingException {
		String message = "OK";
//		Ctv ctv = new Ctv();
//		ctv.setUsername("test");
//		ctv.setPassword("test");
//		ctv.setEmail("hatdaunho1453@gmail.com");
//		ctv.setSdt("111111111111111");
//		ctv.setFullname("test");
//		ctv.setAddress("test");
		List<Ctv> list = new ArrayList<>();
		if(!service.checkUsername(ctv.getUsername())) {
			message = "Username đã tồn tại!";
		} else {
			ctv = service.registerCtv(ctv);
			list.add(ctv);
		}
		return new Response<Ctv>(list, message);
	}
	
	@GetMapping("/list")
	public List<Ctv> tst(){
		return repo.findAll();
	}
	
}
