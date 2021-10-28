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

import com.okteam.dao.CtvRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dao.RegiProductRepository;
import com.okteam.dto.CommentsDTO;
import com.okteam.dto.RegiProductsDTO;
import com.okteam.entity.Comments;
import com.okteam.entity.Ctv;
import com.okteam.entity.Products;
import com.okteam.entity.RegiProducts;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/regi_products")
public class RegiProductController {
	@Autowired
	RegiProductRepository RegiPro;
	@Autowired
	CtvRepository CtvRep;
	@Autowired
	ProductRepository ProRep;

	@GetMapping
	public ResponseEntity<List<RegiProducts>> getAllRegi() {
		return new ResponseEntity<List<RegiProducts>>(RegiPro.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{idregi}")
	public ResponseEntity<RegiProducts> getRegiById(@PathVariable("idregi") Integer idregi) {
		RegiProducts rige = RegiPro.findById(idregi).get();
		ResponseEntity.notFound().build();
		
		return new ResponseEntity<RegiProducts>(rige, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RegiProducts> postRegi(@RequestBody RegiProductsDTO regiproDto) {
		Ctv ctv = CtvRep.findById(regiproDto.getUsername()).get();
		RegiProducts regiproduct = new RegiProducts();
		regiproduct.setIdregi(regiproDto.getIdregi());
		regiproduct.setRegidate(regiproDto.getRegidate());
		regiproduct.setCtv(ctv);
		Products products = ProRep.findById(regiproDto.getIdpro()).get();
		regiproduct.setProducts(products);
	return new ResponseEntity<RegiProducts>(RegiPro.save(regiproduct), HttpStatus.CREATED);
	}
}
