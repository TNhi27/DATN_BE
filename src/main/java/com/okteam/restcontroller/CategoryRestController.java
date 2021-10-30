package com.okteam.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CategoryRepository;
import com.okteam.entity.Category;
import com.okteam.entity.Response;

@CrossOrigin("*")
@RequestMapping("/api/v1")
@RestController
public class CategoryRestController {

	@Autowired
	CategoryRepository categoryRepo;
	
	@GetMapping("/category/list")
	public Response<Category> getCategories(){
		return new Response<Category>(categoryRepo.findAll(), "OK");
	}
	
	@PostMapping("/category/add")
	public String addCategory(@RequestBody Category category) {
		categoryRepo.save(category);
		return "Thêm loại thành công!";
	}
	
}
