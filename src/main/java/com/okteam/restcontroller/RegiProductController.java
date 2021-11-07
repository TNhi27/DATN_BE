package com.okteam.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dao.RegiProductRepository;
import com.okteam.dto.RegiProductsDTO;
import com.okteam.entity.Category;
import com.okteam.entity.Ctv;
import com.okteam.entity.Products;
import com.okteam.entity.RegiProducts;
import com.okteam.entity.Response;

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
		RegiProducts regiproduct = new RegiProducts();
		if(!RegiPro.existsById(regiproDto.getIdregi())) {
			System.out.println("ko tìm thấy");
			   return new ResponseEntity<RegiProducts>(regiproduct, HttpStatus.NOT_FOUND);
		}else if(!CtvRep.existsById(regiproDto.getUsername())){
			System.out.println("CTV không tim thấy");
			   return new ResponseEntity<RegiProducts>(regiproduct, HttpStatus.NOT_FOUND);
		}else if(!ProRep.existsById(regiproDto.getIdpro())) {
			System.out.println("ID sản phẩm không tìm thấy");
		}
		else {
		Ctv ctv = CtvRep.findById(regiproDto.getUsername()).get();
		Products products = ProRep.findById(regiproDto.getIdpro()).get();
		regiproduct.setIdregi(regiproDto.getIdregi());
		regiproduct.setRegidate(regiproDto.getRegidate());
		regiproduct.setCtv(ctv);
		regiproduct.setProducts(products);
		}

	return new ResponseEntity<RegiProducts>(RegiPro.save(regiproduct), HttpStatus.CREATED);
	}
	
    @PutMapping("/{idregi}")
    public ResponseEntity<RegiProducts> updateRegi(@RequestBody RegiProductsDTO regiproDto, @PathVariable Integer idregi) {
    	RegiProducts regipro = RegiPro.findById(idregi).get();
    	if(!RegiPro.existsById(regiproDto.getIdregi())) {
			System.out.println("ko tìm thấy");
			   return new ResponseEntity<RegiProducts>(regipro, HttpStatus.NOT_FOUND);
		}else if(!CtvRep.existsById(regiproDto.getUsername())){
			System.out.println("CTV không tim thấy");
			   return new ResponseEntity<RegiProducts>(regipro, HttpStatus.NOT_FOUND);
		}else if(!ProRep.existsById(regiproDto.getIdpro())) {
			System.out.println("ID sản phẩm không tìm thấy");
		}else {
    		regipro.setRegidate(regiproDto.getRegidate());
    		regipro.setProducts(ProRep.findById(regiproDto.getIdpro()).get());
    		regipro.setCtv(CtvRep.findById(regiproDto.getUsername()).get());
		}
			return  new ResponseEntity<RegiProducts>(RegiPro.save(regipro),HttpStatus.OK);
    }
    
   
    @DeleteMapping("/{idregi}")
	public void deleteRegi(@PathVariable("idregi") Integer idregi){
    	if (!RegiPro.existsById(idregi)) {
    		System.out.println("ko tìm thấy");
		} else {
			RegiPro.deleteById(idregi);
		}
	}
    
}
