package com.okteam.restcontroller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.AdminRepository;
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
	@Autowired
	AdminRepository adRepo;

	@PostMapping("/forget")
	public Response<Object> forget(@RequestParam("username") String username, @RequestParam("email") String email)
			throws UnsupportedEncodingException, MessagingException {
		String message = "OK";
		if (!service.checkUsername(username)) {
			message = "Không tìm thấy tài khoản!";
		} else {
			if (service.isCtv(username) && !email.equalsIgnoreCase(ctvRepo.findById(username).get().getEmail())) {
				return new Response<Object>(null, "Email tài khoản không đúng!");
			}
			if (service.isNcc(username) && !email.equalsIgnoreCase(nccRepo.findById(username).get().getEmail())) {
				return new Response<Object>(null, "Email tài khoản không đúng!");
			}
			if (service.isAdmin(username) && !email.equalsIgnoreCase(adRepo.findById(username).get().getEmail())) {
				return new Response<Object>(null, "Email tài khoản không đúng!");
			}
			service.forgetPW(username);
		}
		return new Response<Object>(null, message);
	}

	@PostMapping("/change")
	public Response<Object> change(@RequestParam("password") String password, @RequestParam("newP") String newP,
			@RequestParam("confirmP") String confirmP) {
		String message = "OK";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if(service.isAdmin(username)) {
			message = "Tài khoản quản trị không dùng chức năng này!";
		} else {
			if(service.isCtv(username) && !password.equalsIgnoreCase(ctvRepo.findById(username).get().getPassword())) {
				return new Response<Object>(null, "Mật khẩu không đúng!");
				
			}
			if(service.isNcc(username) && !password.equalsIgnoreCase(nccRepo.findById(username).get().getPassword())) {
				return new Response<Object>(null, "Mật khẩu không đúng!");
			}
			if(!newP.equalsIgnoreCase(confirmP)) {
				return new Response<Object>(null, "Xác nhận mật khẩu không đúng!");
			}
			service.changePW(username, newP);
		}
		return new Response<Object>(null, message);
	}

}
