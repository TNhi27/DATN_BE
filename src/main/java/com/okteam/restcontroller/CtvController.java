package com.okteam.restcontroller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.okteam.dao.CommentRepository;
import com.okteam.dao.CtvRepository;
import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.RegiProductRepository;
import com.okteam.dto.CtvReqDTO;
import com.okteam.dto.CtvResponseDTO;
import com.okteam.entity.Ctv;
import com.okteam.entity.Response;
import com.okteam.exception.UsersException;
import com.okteam.utils.DtoUtils;
import com.okteam.utils.RegisterService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/ctv")
public class CtvController {

	@Autowired
	RegisterService service;
	@Autowired
	CtvRepository repo;
	@Autowired
	CommentRepository cmtRepo;
	@Autowired
	RegiProductRepository regiRepo;
	@Autowired
	FollowSellRepository flRepo;
	@Autowired
	DtoUtils dtoUtils;
	
	@PostMapping("/info")
	public ResponseEntity<CtvResponseDTO> getInfo() {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();

		Ctv ctv = repo.findById(username).orElseThrow(() -> new UsersException());
		CtvResponseDTO rs = new CtvResponseDTO();
		rs.createByEntity(ctv);

		return new ResponseEntity<CtvResponseDTO>(rs, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Ctv> update(@RequestBody CtvReqDTO dto) {
		Ctv ctv = repo.findById(dto.getUsername()).orElseThrow(() -> new UsersException());
		ctv.setAddress(dto.getAddress());
		ctv.setEmail(dto.getEmail());
		ctv.setFullname(dto.getFullname());
		ctv.setImage(dto.getImage());
		ctv.setSdt(dto.getSdt());

		return new ResponseEntity<Ctv>(repo.save(ctv), HttpStatus.OK);

	}
	
	@GetMapping("/list")
	public Response<CtvResponseDTO> getCtvs(){
		return new Response<CtvResponseDTO>(dtoUtils.mapCtvToDto(repo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null,"OK");
	}
	
	@GetMapping("/check-id/{username}")
	public Boolean checkusernamectv(@PathVariable("username") String username) {
		return service.checkUsername(username);
	}

	@PostMapping("/add")
	public Response<CtvResponseDTO> addCtv(@RequestBody CtvReqDTO ctv){
		String message = "OK";
		if(service.checkUsername(ctv.getUsername())) {
			message = "Tài khoản đã tồn tại, vui lòng chọn tên khác!";
		} else {
			repo.save(new Ctv().dtoReturnEntity(ctv));
		}
		return new Response<CtvResponseDTO>(dtoUtils.mapCtvToDto(repo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, message);
	}
	
	@DeleteMapping("/delete")
	public Response<CtvResponseDTO> deleteCtv(@RequestBody CtvReqDTO ctv){
		String message = "OK";
		if(!service.isCtv(ctv.getUsername())) {
			message = "Tài khoản cộng tác viên không chính xác!";
		} else {
			Ctv c = repo.findById(ctv.getUsername()).get();
			if(c.getOrders().size() > 0) {
				message = "Cộng tác viên đã có đơn hàng!";
			} else {
				regiRepo.deleteAll(c.getList_regi());
				flRepo.deleteAll(c.getFollowSell());
				cmtRepo.deleteAll(c.getComments());
				repo.delete(c);
			}
		}
		return new Response<CtvResponseDTO>(dtoUtils.mapCtvToDto(repo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, message);
	}
	
	@PutMapping("/update-trangthai")
	public Response<CtvResponseDTO> update_trangthai(@RequestParam("username") String username){
		String message = "OK";
		if(!service.isCtv(username)) {
			message = "Tài khoản cộng tác viên không chính xác!";
		} else {
			Ctv ctv =repo.findById(username).get();
			ctv.setActive(!ctv.isActive());
			ctv.setVerify(null);
			repo.save(ctv);
		}
		return new Response<CtvResponseDTO>(null, null, message);
	}
	
	@PutMapping("/reform/{username}")
	public Response<CtvResponseDTO> reformCtv(@PathVariable("username") String username,
			@RequestParam("thaotac") Integer thaotac, @RequestParam("value") String value){
		if(!service.isCtv(username)) {
			return new Response<CtvResponseDTO>(null, null, "Tài khoản cộng tác viên không chính xác!");
		}
		Ctv ctv = repo.findById(username).get();
		switch (thaotac) {
		case 0:
			ctv.setPassword(value);
			break;
		case 1:
			ctv.setFullname(value);
			break;
		case 2:
			ctv.setImage(value);
			break;
		case 3:
			ctv.setSex(value);
			break;
		case 4:
			ctv.setEmail(value);
			break;
		case 5:
			ctv.setSdt(value);
			break;
		case 6:
			ctv.setAddress(value);
			break;
		default:
			return new Response<CtvResponseDTO>(null, null,"Thao tác không hợp lệ!");
		}
		repo.save(ctv);
		return new Response<CtvResponseDTO>(null, null, "OK");
	}
	
	@GetMapping("/getone")
	public Response<CtvResponseDTO> getOne(@RequestParam("username") String username){
		if(!repo.existsById(username)) {
			return new Response<CtvResponseDTO>(null, null, "Tài khoản cộng tác viên không chính xác!");
		}
		CtvResponseDTO ctv = new CtvResponseDTO().createByEntity(repo.findById(username).get());
		return new Response<CtvResponseDTO>(dtoUtils.mapCtvToDto(repo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), ctv, "OK");
	}
	
	@PutMapping("/update-all")
	public Response<CtvResponseDTO> update_all(@RequestBody CtvReqDTO ctv){
		String message = "OK";
		if(!repo.existsById(ctv.getUsername())) {
			message = "Tài khoản cộng tác viên không chính xác!";
		} else {
			Ctv c = repo.findById(ctv.getUsername()).get();
			String password = c.getPassword();
			Date date = c.getCreatedate();
			c = c.dtoReturnEntity(ctv);
			c.setPassword(password);
			c.setCreatedate(date);
			repo.save(c);
		}
		return new Response<CtvResponseDTO>(dtoUtils.mapCtvToDto(repo.findAll(Sort.by(Sort.Direction.DESC, "createdate"))), null, message);
	}
	
}
