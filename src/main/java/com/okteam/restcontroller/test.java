package com.okteam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.security.JwtService;

@RestController
@CrossOrigin
public class test {
	 @Autowired
	    AuthenticationManager authentication;
	 @Autowired
	    JwtService jwtService;

	@RequestMapping("/admin")
	public String name() {
		return "admin";
	}
	
	@RequestMapping("/user")
	public String sasd() {
		return "user";
	}
	@RequestMapping("/ncc")
	public String ncc() {
		return "ncc";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam String username,@RequestParam String password) {
		
		 Authentication auth = authentication.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        SecurityContextHolder.getContext().setAuthentication(auth);
	        String jwt = jwtService.generateJwt(username);
		return jwt;
	}
}
