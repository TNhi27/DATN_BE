package com.okteam.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.RegiProductRepository;
import com.okteam.entity.RegiProducts;
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class RegiProductController {
	@Autowired
	RegiProductRepository RegiPro;
 
	@GetMapping()
	public ResponseEntity<List<RegiProducts>> rest() {
		return new ResponseEntity<List<RegiProducts>>(RegiPro.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{idregi}")
	public ResponseEntity<RegiProducts> getOne(@PathVariable("idregi")Integer idregi){
		if(!RegiPro.existsById(idregi)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(RegiPro.findById(idregi).get());
	}
	
	@PostMapping("/regi_products")
	public ResponseEntity<RegiProducts> post(@RequestBody RegiProducts regipro){
		if(RegiPro.existsById(regipro.getIdregi())) {
			return ResponseEntity.badRequest().build();
		}
		RegiPro.save(regipro);
		return ResponseEntity.ok(regipro);
	}

	
}
