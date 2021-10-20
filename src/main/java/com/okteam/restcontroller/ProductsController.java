package com.okteam.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okteam.dao.ProductRepository;
import com.okteam.entity.Products;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/product")
public class ProductsController {
	
	@Autowired
	ProductRepository productRepository;
 
	@GetMapping()
	public ResponseEntity<List<Products>> rest() {
		return new ResponseEntity<List<Products>>(productRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{name}")
	public Optional<Products> getOne(@PathVariable("name")String name) {
		return productRepository.findById(name);
	}
	
	@GetMapping("/search")
    public ResponseEntity<Page<Products>> getAllProduct(@RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> size, @RequestParam String name) {
		
        System.out.println(productRepository.findById("ADMIN").get());
        
        Page<Products> page = productRepository.findByProducts(name, PageRequest.of(pageNumber.orElse(0), size.orElse(10)));
        return new ResponseEntity<Page<Products>>(page, HttpStatus.OK);
    }

}
