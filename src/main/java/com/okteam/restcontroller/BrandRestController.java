package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.BrandRepository;
import com.okteam.entity.Brand;
import com.okteam.entity.Response;

@CrossOrigin("*")
@RequestMapping("/api/v1")
@RestController
public class BrandRestController {
	
	@Autowired
	BrandRepository brandRepo;
	
	@GetMapping("/brand/list")
	public Response<Brand> getBrands(@RequestParam(value="idcate", required = false) String idcate){
		String message = "Không lấy được dữ liệu";
		List<Brand>list = new ArrayList<>();
		if(idcate == null || idcate == "0") {
			list = brandRepo.findAll();
			message = "OK";
		} else {
			list = brandRepo.findByIdcate(idcate);
			message = "OK";
		}
		return new Response<Brand>(list, message);
	}
}
