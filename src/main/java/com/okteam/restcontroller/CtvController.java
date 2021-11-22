package com.okteam.restcontroller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CtvRepository;
import com.okteam.dto.CtvReqDTO;
import com.okteam.dto.CtvResponseDTO;
import com.okteam.entity.Ctv;
import com.okteam.entity.Response;
import com.okteam.exception.UsersException;
import com.okteam.utils.RegisterService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/ctv")
public class CtvController {

	@Autowired
	RegisterService service;
	@Autowired
	CtvRepository repo;

	@PostMapping("/register")
	public Response<CtvResponseDTO> register(@RequestBody Ctv ctv)
			throws UnsupportedEncodingException, MessagingException {
		String message = "OK";
		List<CtvResponseDTO> list = new ArrayList<>();
		if (service.checkUsername(ctv.getUsername())) {
			message = "Username đã tồn tại!";
		} else {
			ctv = service.registerCtv(ctv);
			CtvResponseDTO ctvDTO = new CtvResponseDTO();
			list.add(ctvDTO.createByEntity(ctv));
		}
		return new Response<CtvResponseDTO>(list, message);
	}

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

}
