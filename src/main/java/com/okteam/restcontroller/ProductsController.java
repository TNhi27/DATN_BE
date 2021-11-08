package com.okteam.restcontroller;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.okteam.dao.BrandRepository;
import com.okteam.dao.CategoryRepository;
import com.okteam.dao.CommentRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.NccResponseDTO;
import com.okteam.dto.Productdto;
import com.okteam.entity.Brand;
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
import org.springframework.data.domain.Sort;
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

    @Autowired
    NccRepository nccRepository;
    @Autowired
    BrandRepository brandRepository;

    // Lấy 1 sản phẩm theo ID
    @GetMapping("/getone/{id}")
    public ResponseEntity<Products> getProduct(@PathVariable("id") String id) {
        Products pro = proDAO.findById(id).orElseThrow(() -> new NotFoundSomething("Khong tim thay san pham"));

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

        List<Rating> l = list.stream().filter(rating -> rating.getRating() >= 4).collect(Collectors.toList());
        return new ResponseEntity<List<Rating>>(l, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<Page<Comments>> getComments(@RequestParam String idpro) {
        Page<Comments> list = commentRepository.getCommentsOfProduct(idpro, PageRequest.of(0, 10));

        return new ResponseEntity<Page<Comments>>(list, HttpStatus.OK);
    }

    @GetMapping("/get_by_category")
    public ResponseEntity<Page<Products>> getproductbycate(@RequestParam Optional<String> category) {
        Page<Products> page = proDAO.getProductsByCate1(category.orElse("%%"), PageRequest.of(0, 15));

        return new ResponseEntity<Page<Products>>(page, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Products>> newProducts(@RequestParam Optional<Integer> p,
            @RequestParam Optional<Integer> size, @RequestParam Optional<String> category,
            @RequestParam Optional<Integer> min_price, @RequestParam Optional<Integer> max_price,
            @RequestParam Optional<List<String>> origin, @RequestParam Optional<List<String>> city,
            @RequestParam Optional<Integer> brand, @RequestParam Optional<String> sort,
            @RequestParam Optional<Boolean> des) {

        Sort s;
        Page page;
        if (des.orElse(true) == true) {
            s = Sort.by(sort.orElse("createdate")).descending();
        } else {
            s = Sort.by(sort.orElse("createdate"));
        }

        List<String> root_origin = proDAO.getRootOrigin();
        List<String> root_city_ncc = proDAO.getRootCityNcc();

        if (brand.isPresent()) {
            page = proDAO.getProductsByCategoryHasBrand(category.orElse("%%"), min_price.orElse(0),
                    max_price.orElse(1000000000), origin.orElse(root_origin), city.orElse(root_city_ncc), brand.get(),
                    PageRequest.of(p.orElse(0), size.orElse(25), s));

        } else {
            page = proDAO.getProductsByCategory(category.orElse("%%"), min_price.orElse(0),
                    max_price.orElse(1000000000), origin.orElse(root_origin), city.orElse(root_city_ncc),
                    PageRequest.of(p.orElse(0), size.orElse(25), s));
        }
        // page = proDAO.getProductsByCategoryHasBrand("PK1", 0, 10000000,
        // origin.orElse(root_origin),
        // city.orElse(root_city_ncc), 17, PageRequest.of(0, 10));

        return new ResponseEntity<Page<Products>>(page, HttpStatus.OK);
    }

    @GetMapping("/origins")
    public ResponseEntity<List<String>> get_origin() {
        return new ResponseEntity<List<String>>(proDAO.getRootOrigin(), HttpStatus.OK);
    }

    @GetMapping("/city_ncc")
    public ResponseEntity<List<String>> get_city_ncc() {
        return new ResponseEntity<List<String>>(proDAO.getRootCityNcc(), HttpStatus.OK);
    }

    // post
    @PostMapping
    public ResponseEntity<Products> saveProducts(@RequestBody Productdto productdto) {
        Products pro = new Products();
        pro.setIdpro(productdto.getIdpro());
        pro.setName(productdto.getName());
        pro.setDescription(productdto.getDescription());
        pro.setPricectv(productdto.getPricectv());
        pro.setCreatedate(productdto.getCreatedate());
        pro.setTags(productdto.getTags());
        pro.setQty(productdto.getQty());
        pro.setDvt(productdto.getDvt());
        pro.setImage0(productdto.getImage0());
        pro.setImage1(productdto.getImage1());
        pro.setImage2(productdto.getImage2());
        pro.setImage3(productdto.getImage3());
        pro.setOrigin(productdto.getOrigin());
        pro.setCategory(cateDAO.findById(productdto.getIdcate()).get());
        pro.setNcc(nccRepository.findById(productdto.getUsername()).get());
        pro.setActive(productdto.isActive());
        pro.setP_brand(brandRepository.findById(productdto.getIdbrand()).get());
        return new ResponseEntity<Products>(proDAO.save(pro), HttpStatus.OK);
    }

    // put=update
    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProducts(@PathVariable("id") String id, @RequestBody Productdto productdto) {
        Products pro = proDAO.findById(id).get();
        pro.setName(productdto.getName());
        pro.setDescription(productdto.getDescription());
        pro.setPricectv(productdto.getPricectv());
        pro.setCreatedate(productdto.getCreatedate());
        pro.setTags(productdto.getTags());
        pro.setQty(productdto.getQty());
        pro.setDvt(productdto.getDvt());
        pro.setImage0(productdto.getImage0());
        pro.setImage1(productdto.getImage1());
        pro.setImage2(productdto.getImage2());
        pro.setImage3(productdto.getImage3());
        pro.setOrigin(productdto.getOrigin());
        pro.setCategory(cateDAO.findById(productdto.getIdcate()).get());
        pro.setNcc(nccRepository.findById(productdto.getUsername()).get());
        pro.setActive(productdto.isActive());
        pro.setP_brand(brandRepository.findById(productdto.getIdbrand()).get());
        return new ResponseEntity<Products>(proDAO.save(pro), HttpStatus.OK);
    }

    // xóa
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") String id) {
        proDAO.deleteById(id);
    }

    @GetMapping("/search")
    public List<Products> search(@RequestParam String q) {
        List<Products> list = proDAO.search(q);
        return list;
    }
}
