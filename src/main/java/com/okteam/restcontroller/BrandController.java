package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.BrandRepository;
import com.okteam.dao.CategoryRepository;
import com.okteam.entity.Brand;
import com.okteam.entity.Category;
import com.okteam.entity.Response;

@CrossOrigin("*")
@RequestMapping("/api/v1")
@RestController
public class BrandController {
	
	@Autowired
	BrandRepository brandRepo;
	@Autowired
	CategoryRepository categoryRepo;
	
	@GetMapping("/brand/list")
	public Response<Brand> getBrands(@RequestParam(value="idcate", required = false) String idcate){
		String message = "Không lấy được dữ liệu";
		List<Brand>list = new ArrayList<>();
		if(idcate == null || idcate.equals("0") ) {
			list = brandRepo.findAll();
			message = "OK";
		} else {
			list = brandRepo.findByIdcate(idcate);
			message = "OK";
		}
		return new Response<Brand>(list, message);
	}
	
	@PostMapping("/brand/addTo/{idcate}")
	public Response<Brand> addBrand(@PathVariable("idcate") String idcate, @RequestBody Brand brand){
		String message = "OK";
		Category c = categoryRepo.findById(idcate).get();
		brand.setBr_category(c);
		brandRepo.save(brand);
		return new Response<Brand>(brandRepo.findAll(), message);
	}
}
