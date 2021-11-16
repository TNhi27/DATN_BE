package com.okteam.restcontroller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CtvRepository;
import com.okteam.dto.CtvResponseDTO;
import com.okteam.entity.Ctv;
import com.okteam.entity.Response;
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
	public Response<CtvResponseDTO> register(@RequestBody Ctv ctv) throws UnsupportedEncodingException, MessagingException {
		String message = "OK";
		List<CtvResponseDTO> list = new ArrayList<>();
		if(service.checkUsername(ctv.getUsername())) {
			message = "Username đã tồn tại!";
		} else {
			ctv = service.registerCtv(ctv);
			CtvResponseDTO ctvDTO = new CtvResponseDTO();
			list.add(ctvDTO.createByEntity(ctv));
		}
		return new Response<CtvResponseDTO>(list, message);
	}
	
}
