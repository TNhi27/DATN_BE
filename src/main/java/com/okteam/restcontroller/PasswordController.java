package com.okteam.restcontroller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.NccRepository;
import com.okteam.entity.Response;
import com.okteam.utils.RegisterService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/password")
public class PasswordController {

	@Autowired
	RegisterService service;
	@Autowired
	CtvRepository ctvRepo;
	@Autowired
	NccRepository nccRepo;
	
	@PostMapping("/forget")
	public Response<Object> forget(@RequestParam("username") String username, @RequestParam("email") String email) throws UnsupportedEncodingException, MessagingException {
		String message = "OK";
		if(!service.checkUsername(username)) {
			message = "Không tìm thấy tài khoản!";
		} else if(service.isAdmin(username)) {
			message ="Tài khoản quản trị không dùng chức năng này!";
		} else {
			if(service.isCtv(username) && !email.equalsIgnoreCase(ctvRepo.findById(username).get().getEmail())) {
				return new Response<Object>(null, "Email tài khoản không đúng!");
			}
			if(service.isNcc(username) && !email.equalsIgnoreCase(nccRepo.findById(username).get().getEmail())) {
				return new Response<Object>(null, "Email tài khoản không đúng!");
			}
			service.forgetPW(username);
		}
		return new Response<Object>(null, message);
	}
	
}
