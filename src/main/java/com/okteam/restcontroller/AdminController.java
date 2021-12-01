package com.okteam.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.AdminRepository;
import com.okteam.dao.PostRepository;
import com.okteam.dto.AdminDto;
import com.okteam.dto.AdminResponseDto;
import com.okteam.entity.Admin;
import com.okteam.entity.Response;
import com.okteam.utils.DtoUtils;
import com.okteam.utils.RegisterService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Autowired
	AdminRepository adRepo;
	@Autowired
	PostRepository postRepo;
	@Autowired
	DtoUtils dtoUtils;
	@Autowired
	RegisterService service;
	
	@GetMapping("/list")
	public Response<AdminResponseDto> allAdmin() {
		return new Response<AdminResponseDto>(dtoUtils.mapAdminToDto(adRepo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, "OK");
	}
	
	@GetMapping("/check-id/{username}")
	public Boolean checkusername(@PathVariable("username") String username) {
		return service.checkUsername(username);
	}
	
	@PostMapping("/add")
	public Response<AdminResponseDto> addAdmin(@RequestBody AdminDto ad) {
		String message = "OK";
		if(service.checkUsername(ad.getUsername())) {
			message = "Tài khoản đã tồn tại, vui lòng chọn tên khác!";
		} else {
			adRepo.save(new Admin().dtoReturnEntity(ad));
		}
		return new Response<AdminResponseDto>(dtoUtils.mapAdminToDto(adRepo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, message);
	}
	
	@DeleteMapping("/delete")
	public Response<AdminResponseDto> deleteAdmin(@RequestBody AdminDto ad) {
		String messgae = "OK";
		if(!service.isAdmin(ad.getUsername())) {
			messgae = "Tài khoản admin không chính xác!";
		} else {
//			postRepo.deleteAll(adRepo.findById(ad.getUsername()).get().getPosts());
			adRepo.deleteById(ad.getUsername());
		}
		return new Response<AdminResponseDto>(dtoUtils.mapAdminToDto(adRepo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, messgae);
	}
	
}
