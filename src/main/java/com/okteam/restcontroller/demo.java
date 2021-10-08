package com.okteam.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo {
	@GetMapping("/get")
	public String name() {
		return "hello";
	}
	@GetMapping("/get1")
	public String name1() {
		return "hello1";
	}
	@GetMapping("/get1")
	public String name12() {
		return "hello12";
	}
}
