package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.okteam.dao.CategoryRepository;
import com.okteam.dao.CommentRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.NccResponseDTO;
import com.okteam.entity.Category;
import com.okteam.entity.Comments;
import com.okteam.entity.Products;
import com.okteam.entity.Rating;
import com.okteam.exception.NotFoundSomething;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/products")
public class ProductsController {
    @Autowired
    ProductRepository proDAO;
    @Autowired
    CategoryRepository cateDAO;
    
    @Autowired
    CommentRepository commentRepository;

    // Lấy 1 sản phẩm theo ID
    @GetMapping("/getone/{id}")	
    public ResponseEntity<Products> getProduct(@PathVariable("id") String id) {
        Products pro = proDAO.findById(id).orElseThrow(()->new NotFoundSomething("Khong tim thay san pham"));
       
        return new ResponseEntity<Products>(pro, HttpStatus.OK);
    }

    // Lấy n sản phẩm mới nhứt
    @GetMapping("/new")
    public ResponseEntity<List<Products>> getProducts(@RequestParam Optional<Integer> num) {
        try {
            List<Products> sp = proDAO.findAll();
            Collections.sort(sp, new Comparator<Products>() {
                @Override
                public int compare(Products o1, Products o2) {
                    return o2.getCreatedate().compareTo(o1.getCreatedate());
                }
            });
            List<Products> sp1 = new ArrayList<>();
            for (int i = 0; i < num.orElse(10); i++) {
                sp1.add(sp.get(i));
            }
            return new ResponseEntity<List<Products>>(sp1, HttpStatus.OK);

        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/search_by_name")
    public ResponseEntity<Page<Products>> getAllProduct(@RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> size, @RequestParam String name) {

        Page<Products> page = proDAO.findByProducts(name, PageRequest.of(pageNumber.orElse(0), size.orElse(10)));
        return new ResponseEntity<Page<Products>>(page, HttpStatus.OK);

    }
    
    @GetMapping("/top_star")
    public ResponseEntity<List<Rating>> get5star() {
		Page<Rating> list = proDAO.getProductsWith5Star(PageRequest.of(0, 10));
		
		List<Rating> l = list.stream().filter(rating-> rating.getRating()>=4).collect(Collectors.toList());
		return new ResponseEntity<List<Rating>>(l, HttpStatus.OK);
	}
    
    @GetMapping("/comments")
    public ResponseEntity<Page<Comments>> getComments(@RequestParam String idpro) {
		Page<Comments> list = commentRepository.getCommentsOfProduct(idpro, PageRequest.of(0, 10));
		
		return new ResponseEntity<Page<Comments>>(list, HttpStatus.OK);
	}
    
    
}
