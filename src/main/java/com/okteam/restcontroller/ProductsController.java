package com.okteam.restcontroller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.okteam.dao.CateDAO;
import com.okteam.dao.ProductDAO;
import com.okteam.entity.Category;
import com.okteam.entity.Products;

import org.springframework.beans.factory.annotation.Autowired;

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

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ProductsController {
    @Autowired
    ProductDAO proDAO;
    @Autowired
    CateDAO cateDAO;

    // Lấy 1 sản phẩm theo ID
    @GetMapping("/api/v1/products/{id}")
    public ResponseEntity<Products> getProduct(@PathVariable("id") String id) {
        Products pro = proDAO.findById(id).get();
        System.out.println(pro.getIdpro());
        return new ResponseEntity<Products>(pro, HttpStatus.OK);
    }

    // Lấy 10 sản phẩm mới nhứt
    @GetMapping("/api/v1/products/new")
    public ResponseEntity<List<Products>> getProducts() {
        try {
            List<Products> sp = proDAO.findAll();
            Collections.sort(sp, new Comparator<Products>() {
                @Override
                public int compare(Products o1, Products o2) {
                    return o2.getCreatedate().compareTo(o1.getCreatedate());
                }
            });
            List<Products> sp1 = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                sp1.add(sp.get(i));
            }
            return new ResponseEntity<List<Products>>(sp1, HttpStatus.OK);

        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // Lấy 20 dssp cùng loại
    @GetMapping("/api/v1/ncc/products")
    public ResponseEntity<List<Products>> getPro(@RequestParam String id) {
        List<Products> same = proDAO.getProductsByCate(id);
        return new ResponseEntity<List<Products>>(same, HttpStatus.OK);

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
