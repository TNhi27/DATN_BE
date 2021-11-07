package com.okteam.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CategoryRepository;
import com.okteam.entity.Category;
import com.okteam.entity.Response;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/category")
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepo;

	@GetMapping
	public ResponseEntity<List<Category>> get_all() {
		return new ResponseEntity<List<Category>>(categoryRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public Response<Category> getCategories() {
		return new Response<Category>(categoryRepo.findAll(), "OK");
	}

	@PostMapping("/add")
	public Response<Category> addCategory(@RequestBody Category category) {
		String message = "OK";
		if (categoryRepo.existsById(category.getIdcate())) {
			message = "Mã loại đã tồn tại!";
		} else if (category.getParent() != null && !categoryRepo.existsById(category.getParent())) {
			message = "Không tìm thấy loại hàng!";
		} else {
			categoryRepo.save(category);
		}
		return new Response<Category>(categoryRepo.findAll(), message);
	}

	@PutMapping("/update")
	public Response<Category> updateCategory(@RequestParam(name = "idcate") String idcate,
			@RequestParam(name = "value") String value, @RequestParam(name="thaotac") Integer thaotac) {
		Category category = categoryRepo.findById(idcate).get();
		String message = "Không tìm thấy thao tác!";
		if(thaotac == 0) {
			if(value.isEmpty()) {
				message = "Không để trống tên loại!";
			} else {
				category.setTypename(value);
				categoryRepo.save(category);
				message = "OK";
			}
		}
		if(thaotac == 1) {
			category.setImg(value);
			categoryRepo.save(category);
			message = "OK";
		}
		if(thaotac == 2) {
			if(!value.isEmpty() && !categoryRepo.existsById(value)) {
				message = "Không tìm thấy loại hàng!";
			} else {
				if(value.isEmpty()) {
					value = null;
				}
				category.setParent(value);
				categoryRepo.save(category);
				message = "OK";
			}
		}
		return new Response<Category>(null, message);
	}
	
	@DeleteMapping("/delete")
	public Response<Category> deleteCategory(@RequestParam(name="idcate") String idcate){
		String message = "Không tìm thấy loại hàng!";
		if(categoryRepo.existsById(idcate)) {
			Category c = categoryRepo.findById(idcate).get();
			if(c.getBrands().size() > 0) {
				message = "Loại hàng đã có nhãn hàng!";
			} else if(c.getProducts().size() > 0) {
				message = "Loại hàng đã có sản phẩm!";
			} else {
				if(categoryRepo.findByParent(idcate).size() > 0) {
					message = "Loại hàng này có loại con!";
				} else {
					categoryRepo.deleteById(idcate);
					message = "OK";
				}
			}
		}
		return new Response<Category>(categoryRepo.findAll(), message);
	}
}
