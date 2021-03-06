package com.okteam.restcontroller;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.AdminRepository;
import com.okteam.dao.PostRepository;
import com.okteam.dto.AdminDto;
import com.okteam.dto.AdminResponseDto;
import com.okteam.entity.Admin;
import com.okteam.entity.Response;
import com.okteam.utils.DtoUtils;
import com.okteam.utils.RegisterService;

@RestController
@CrossOrigin("*")
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
	public Response<AdminResponseDto> allAdmin(){
		return new Response<AdminResponseDto>(dtoUtils.mapAdminToDto(adRepo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, "OK");
	}
	
	@GetMapping("/check-id/{username}")
	public Boolean checkusername(@PathVariable("username") String username){
		return service.checkUsername(username);
	}
	
	@PostMapping("/add")
	public Response<AdminResponseDto> addAdmin(@RequestBody AdminDto ad){
		String message = "OK";
		if(service.checkUsername(ad.getUsername())) {
			message = "Tài khoản đã tồn tại, vui lòng chọn tên khác!";
		} else {
			adRepo.save(new Admin().dtoReturnEntity(ad));
		}
		return new Response<AdminResponseDto>(dtoUtils.mapAdminToDto(adRepo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, message);
	}
	
	@DeleteMapping("/delete")
	public Response<AdminResponseDto> deleteAdmin(@RequestBody AdminDto ad){
		String messgae = "OK";
		if(!service.isAdmin(ad.getUsername())) {
			messgae = "Tài khoản quản trị viên không chính xác!";
		} else {
//			postRepo.deleteAll(adRepo.findById(ad.getUsername()).get().getPosts());
			adRepo.deleteById(ad.getUsername());
		}
		return new Response<AdminResponseDto>(dtoUtils.mapAdminToDto(adRepo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, messgae);
	}
	
	@PutMapping("/update-trangthai")
	public Response<AdminResponseDto> update_trangthai(@RequestParam("username") String username){
		String message = "OK";
		if(!service.isAdmin(username)) {
			message = "Tài khoản quản trị viên không chính xác!";
		} else {
			Admin ad = adRepo.findById(username).get();
			ad.setActive(!ad.isActive());
			adRepo.save(ad);
		}
		return new Response<AdminResponseDto>(null, null, message);
	}
	
	@PutMapping("/reform/{username}")
	public Response<AdminResponseDto> reformAd(@PathVariable("username") String username, 
			@RequestParam("thaotac") Integer thaotac, @RequestParam("value") String value){
		if(!service.isAdmin(username)) {
			return new Response<AdminResponseDto>(null, null, "Tài khoản quản trị viên không chính xác!");
		}
		Admin ad = adRepo.findById(username).get();
		Integer arr[] = { 1, 6 };
		if(Arrays.asList(arr).contains(thaotac) && value.isEmpty()) {
			return new Response<AdminResponseDto>(null, ad, "Giá trị không hợp lệ!");
		}
		switch (thaotac) {
		case 0:
			ad.setPassword(value);
		case 1:
			ad.setFullname(value);
			break;
		case 2:
			ad.setImage(value);
			break;
		case 3:
			ad.setSex(value);
			break;
		case 4:
			ad.setEmail(value);
			break;
		case 5:
			ad.setSdt(value);
			break;
		case 6:
			ad.setAddress(value);
			break;
		default:
			return new Response<AdminResponseDto>(null, null, "Thao tác không hợp lệ");
		}
		adRepo.save(ad);
		return new Response<AdminResponseDto>(null, null, "OK");
	}
	
	@GetMapping("/getone")
	public Response<AdminResponseDto> getOne(@RequestParam("username") String username){
		if(!adRepo.existsById(username)) {
			return new Response<AdminResponseDto>(null, null, "Tài khoản quản trị viên không chính xác!");
		}
		AdminResponseDto ad = new AdminResponseDto().createByEntity(adRepo.findById(username).get());
		return new Response<AdminResponseDto>(dtoUtils.mapAdminToDto(adRepo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), ad, "OK");
	}
	
	@PutMapping("/update-all")
	public Response<AdminResponseDto> update_all(@RequestBody AdminDto ad){
		String message = "OK";
		if(!adRepo.existsById(ad.getUsername())) {
			message = "Tài khoản quản trị viên không chính xác!";
		} else {
			Admin a = adRepo.findById(ad.getUsername()).get();
			String password = a.getPassword();
			Date date = a.getCreatedate();
			a = a.dtoReturnEntity(ad);
			a.setPassword(password);
			a.setCreatedate(date);
			adRepo.save(a);
		}
		return new Response<AdminResponseDto>(dtoUtils.mapAdminToDto(adRepo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))),
				new AdminResponseDto().createByEntity(adRepo.findById(ad.getUsername()).get()), message);
	}
}
