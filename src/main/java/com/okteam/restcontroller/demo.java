package com.okteam.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo {
	
	@GetMapping("/get")
	public String name() {
		return "hellooooooooo";
	}
	
	@GetMapping("/ge1t")
	public String post() {
		return "hello232";
	}
	
}
