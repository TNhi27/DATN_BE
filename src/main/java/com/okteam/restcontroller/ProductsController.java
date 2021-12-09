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
import com.okteam.dao.OrderRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dao.PropertiesReponsitory;
import com.okteam.dao.RegiProductRepository;
import com.okteam.dto.NccResponseDTO;
import com.okteam.dto.Productdto;
import com.okteam.dto.ProductsResponseDTO;
import com.okteam.entity.Brand;
import com.okteam.entity.Category;
import com.okteam.entity.Comments;
import com.okteam.entity.Details;
import com.okteam.entity.Ncc;
import com.okteam.entity.Orders;
import com.okteam.entity.Products;
import com.okteam.entity.Properties;
import com.okteam.entity.Rating;
import com.okteam.entity.Response;
import com.okteam.exception.NotFoundSomething;
import com.okteam.utils.DtoUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DtoUtils dtoUtils;

    @Autowired
    PropertiesReponsitory propertiesReponsitory;

    @Autowired
    RegiProductRepository regiRepo;

    // Lấy 1 sản phẩm theo ID
    @GetMapping("/getone/{id}")
    public ResponseEntity<Products> getProduct(@PathVariable("id") String id) {
        Products pro = proDAO.findById(id, true);

        return new ResponseEntity<Products>(pro, HttpStatus.OK);
    }

    // Lấy n sản phẩm mới nhứt
    @GetMapping("/new")
    public ResponseEntity<List<Products>> getProducts(@RequestParam Optional<Integer> num) {
        try {
            List<Products> sp = proDAO.findAll(true);
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
        Page<Products> page = proDAO.getProductsByCate1(category.orElse("%%"), PageRequest.of(0, 10));

        return new ResponseEntity<Page<Products>>(page, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Products>> newProducts(@RequestParam Optional<Integer> p,
            @RequestParam Optional<Integer> size, @RequestParam Optional<String> category,
            @RequestParam Optional<Integer> min_price, @RequestParam Optional<Integer> max_price,
            @RequestParam Optional<List<String>> origin, @RequestParam Optional<List<String>> city,
            @RequestParam Optional<Integer> brand, @RequestParam Optional<String> sort,
            @RequestParam Optional<Boolean> des, @RequestParam Optional<String> q,
            @RequestParam Optional<String> parent) {

        Sort s;
        Page page;
        if (des.orElse(true) == true) {
            s = Sort.by(sort.orElse("createdate")).descending();
        } else {
            s = Sort.by(sort.orElse("createdate"));
        }

        List<String> root_origin = proDAO.getRootOrigin();
        List<String> root_city_ncc = proDAO.getRootCityNcc();
        List<Category> children = cateDAO.findByParent(parent.orElse(""));
        if (children.size() > 0) {
            List<String> cate = children.stream().map((e) -> {

                return e.getIdcate();

            }).collect(Collectors.toList());
            page = proDAO.getProductsByParentCategory(cate, min_price.orElse(0), max_price.orElse(Integer.MAX_VALUE),
                    origin.orElse(root_origin), city.orElse(root_city_ncc), "%" + q.orElse("") + "%",
                    PageRequest.of(p.orElse(0), size.orElse(25), s));
            return new ResponseEntity<Page<Products>>(page, HttpStatus.OK);

        }

        page = proDAO.getProductsByCategory(category.orElse("%%"), min_price.orElse(0),
                max_price.orElse(Integer.MAX_VALUE),
                origin.orElse(root_origin), city.orElse(root_city_ncc), "%" + q.orElse("") + "%",
                PageRequest.of(p.orElse(0), size.orElse(25), s));

        // page = proDAO.getProductsByCategoryHasBrand("PK1", 0, 10000000,
        // origin.orElse(root_origin),
        // city.orElse(root_city_ncc), 17, PageRequest.of(0, 10));

        System.out.println(page.getContent().size());

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
        if (proDAO.existsById(productdto.getIdpro())) {
            System.out.print("Mã đã tồn tại");
            return new ResponseEntity<Products>(pro, HttpStatus.NOT_FOUND);
        } else if (!cateDAO.existsById(productdto.getIdcate())) {
            System.out.print("ID loại sản phẩm không tồn tại");
            return new ResponseEntity<Products>(pro, HttpStatus.NOT_FOUND);
        } else if (!brandRepository.existsById(productdto.getIdbrand())) {
            System.out.print("Brand không tồn tại");
            return new ResponseEntity<Products>(pro, HttpStatus.NOT_FOUND);
        }
        pro.setIdpro(productdto.getIdpro());
        pro.setName(productdto.getName());
        pro.setDescription(productdto.getDescription());
        pro.setPricectv(productdto.getPricectv());
        pro.setCreatedate(new Date());
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
        pro.setActive(true);
        pro.setP_brand(brandRepository.findById(productdto.getIdbrand()).get());

        Products p = proDAO.save(pro);

        for (var e : productdto.getProperties()) {
            Properties properties = new Properties();
            properties.setP_properties(p);
            properties.setKeyp(e.getKeyp());
            properties.setValuep(e.getValuep());
            propertiesReponsitory.save(properties);
        }

        return new ResponseEntity<Products>(p, HttpStatus.OK);

    }

    // put=update
    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProducts(@PathVariable("id") String id, @RequestBody Productdto productdto) {
        Products pro = proDAO.findById(id).orElseThrow(() -> new NotFoundSomething(":("));
        if (proDAO.existsById(productdto.getIdpro())) {
            System.out.print("Mã đã tồn tại");
            return new ResponseEntity<Products>(pro, HttpStatus.NOT_FOUND);
        } else if (!cateDAO.existsById(productdto.getIdcate())) {
            System.out.print("ID loại sản phẩm không tồn tại");
            return new ResponseEntity<Products>(pro, HttpStatus.NOT_FOUND);
        } else if (!brandRepository.existsById(productdto.getIdbrand())) {
            System.out.print("Brand không tồn tại");
            return new ResponseEntity<Products>(pro, HttpStatus.NOT_FOUND);
        }
        pro.setName(productdto.getName());
        pro.setDescription(productdto.getDescription());
        pro.setPricectv(productdto.getPricectv());
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
        pro.setActive(true);
        pro.setP_brand(brandRepository.findById(productdto.getIdbrand()).get());
        proDAO.save(pro);
        for (var prt : pro.getProperties()) {
          
            propertiesReponsitory.deleteById(prt.getId());
        }
       

        for (var e : productdto.getProperties()) {
            Properties properties = new Properties();
            properties.setP_properties(pro);
            properties.setKeyp(e.getKeyp());
            properties.setValuep(e.getValuep());
            propertiesReponsitory.save(properties);
        }

        return new ResponseEntity<Products>(pro, HttpStatus.OK);
    }
    // xóa
    // @DeleteMapping("/{id}")
    // public void deleteProduct(@PathVariable("id") String idpro) {
    // //proDAO.getById(idpro.)
    // Products pro = proDAO.findById(idpro).get();
    // pro.setActive(false);

    // }
    @DeleteMapping("/{id}")
    public ResponseEntity<Products> deletePro(@PathVariable("id") String idpro) {
        try {
            Products pro = proDAO.findById(idpro).get();
            pro.setActive(false);
            return new ResponseEntity<Products>(proDAO.save(pro), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public Response<ProductsResponseDTO> getAllProducts(
            @RequestParam(value = "username", required = false) String username) {
        String message = "Không lấy được dữ liệu";
        List<Products> list = proDAO.findAll(Sort.by(Direction.DESC, "createdate"));
        if (username == null) {
            message = "OK";
        } else {
            message = "OK";
            list = proDAO.findByIdNcc(username);
        }
        return new Response<ProductsResponseDTO>(dtoUtils.mapProductsToDto(list), null, message);
    }

    @GetMapping("/check-id/{idpro}")
    public Boolean checkidpro(@PathVariable("idpro") String idpro) {
        return proDAO.existsById(idpro);
    }

    @PostMapping("/add")
    public Response<ProductsResponseDTO> addSP(@RequestBody Productdto p) {
        String message = "OK";
        if (proDAO.existsById(p.getIdpro())) {
            message = "Mã sản phẩm đã tồn tại, vui lòng chọn mã khác!";
        } else {
            Category c = cateDAO.findById(p.getIdcate()).get();
            Brand b = brandRepository.findById(p.getIdbrand()).get();
            Ncc ncc = nccRepository.findById(p.getUsername()).get();
            Products product = new Products().dtoReturnEntity(p);
            product.setCategory(c);
            product.setP_brand(b);
            product.setNcc(ncc);
            proDAO.save(product);
        }
        return new Response<ProductsResponseDTO>(
                dtoUtils.mapProductsToDto(proDAO.findAll(Sort.by(Direction.DESC, "createdate"))), null, message);
    }

    @PutMapping("/update-all")
    public Response<ProductsResponseDTO> updateAll(@RequestBody Productdto p) {
        String message = "OK";
        if (!proDAO.existsById(p.getIdpro())) {
            message = "Không tìm thấy sản phẩm!";
        } else {
            Category c = cateDAO.findById(p.getIdcate()).get();
            Brand b = brandRepository.findById(p.getIdbrand()).get();
            Ncc ncc = nccRepository.findById(p.getUsername()).get();
            Products product = proDAO.findById(p.getIdpro()).get();
            product.setCategory(c);
            product.setP_brand(b);
            product.setNcc(ncc);
            proDAO.save(product);
        }
        return new Response<ProductsResponseDTO>(
                dtoUtils.mapProductsToDto(proDAO.findAll(Sort.by(Direction.DESC, "createdate"))), null, message);
    }

    @DeleteMapping("/delete")
    public Response<ProductsResponseDTO> deleteSP(@RequestBody Productdto p) {
        String message = "OK";
        if (!proDAO.existsById(p.getIdpro())) {
            message = "Không tìm thấy sản phẩm!";
        } else {
            Products pro = proDAO.findById(p.getIdpro()).get();
            if (pro.getDetails().size() > 0) {
                message = "Sản phẩm đã có trong đơn hàng!";
            } else {
                regiRepo.deleteAll(pro.getList_regi());
                commentRepository.deleteAll(pro.getComments());
                propertiesReponsitory.deleteAll(pro.getProperties());
                proDAO.delete(pro);
            }
        }
        return new Response<ProductsResponseDTO>(
                dtoUtils.mapProductsToDto(proDAO.findAll(Sort.by(Direction.DESC, "createdate"))), null, message);
    }

    @PutMapping("/update-trangthai")
    public Response<ProductsResponseDTO> update_trangthai(@RequestParam("idpro") String idpro) {
        String message = "OK";
        if (!proDAO.existsById(idpro)) {
            message = "Không tìm thấy sản phẩm!";
        } else {
            Products p = proDAO.findById(idpro).get();
            p.setActive(!p.isActive());
            proDAO.save(p);
        }
        return new Response<ProductsResponseDTO>(null, null, message);
    }

    @PutMapping("/reform/{idpro}")
    public Response<ProductsResponseDTO> reformSP(@PathVariable("idpro") String idpro,
            @RequestParam("thaotac") Integer thaotac, @RequestParam("value") String value) {
        if (!proDAO.existsById(idpro)) {
            return new Response<ProductsResponseDTO>(null, null, "Không tìm thấy sản phẩm!");
        }
        Products p = proDAO.findById(idpro).get();
        switch (thaotac) {
            case 0:
                p.setName(value);
                break;
            case 1:
                p.setPricectv(Integer.parseInt(value));
                break;
            case 2:
                p.setQty(Integer.parseInt(value));
                break;
            case 3:
                p.setOrigin(value);
                break;
            case 4:
                p.setDvt(value);
                break;
            case 5:
                p.setDescription(value);
                break;
            case 6:
                p.setImage0(value);
                break;
            case 7:
                p.setImage1(value);
                break;
            case 8:
                p.setImage2(value);
                break;
            case 9:
                p.setImage3(value);
                break;
            default:
                return new Response<ProductsResponseDTO>(null, null, "Thao tác không hợp lệ!");
        }
        proDAO.save(p);
        return new Response<ProductsResponseDTO>(null, null, "OK");
    }
}
