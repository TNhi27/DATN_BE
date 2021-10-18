package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Optional;

import com.okteam.dao.CateDAO;
import com.okteam.dao.ProductDAO;
import com.okteam.entity.Category;
import com.okteam.entity.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;


@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    ProductDAO proDAO;
    @Autowired
    CateDAO cateDAO;
    //Lấy 1 sản phẩm theo ID
    @GetMapping("/pro/{id}")
    public  ResponseEntity<Products> getProduct(@PathVariable("id") String id){
        Products pro= proDAO.getById(id);
        return new ResponseEntity<Products>(pro,HttpStatus.OK);
    }
    //Lấy 10 sản phẩm mới nhứt
    @GetMapping("/pro/{idsp}")
    public ResponseEntity<Products> getProducts(@PathVariable("idsp")String idsp){
        Products product=proDAO.getById(idsp);
        return new ResponseEntity<Products>(product,HttpStatus.OK);
    }
    //Lấy 20 dssp cùng loại
    @GetMapping("/product/{idcate}")
    public ResponseEntity<Products> getPro(@PathVariable("idcate")String idcate){
        Optional<Category> c=cateDAO.findById(idcate);
        Category cate=c.get();

        Category same=proDAO.getById(cate.getCategory().getIdcate());
        Products psame=new Products();
        for(int i=0; i<1;i++){
           
        }
        //return new ResponseEntity<Products>(,HttpStatus.OK);
        }
    
    
}
