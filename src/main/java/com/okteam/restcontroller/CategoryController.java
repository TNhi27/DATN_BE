package com.okteam.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CategoryRepository;
import com.okteam.entity.Category;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/category")
public class CategoryController {
	@Autowired
	CategoryRepository cdao;

	@GetMapping
	public ResponseEntity<List<Category>> get_all() {
		return new ResponseEntity<List<Category>>(cdao.findAll(), HttpStatus.OK);
	}
}
