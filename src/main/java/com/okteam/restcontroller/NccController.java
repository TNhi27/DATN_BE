package com.okteam.restcontroller;

import java.util.List;
import java.util.Optional;

import com.okteam.dao.NccRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.entity.Ncc;
import com.okteam.entity.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/api/v1/ncc")
public class NccController {

    @Autowired
    NccRepository nccRepository;
    @Autowired
    ProductRepository productDAO;

    //
    @GetMapping("/get/{idncc}")
    public ResponseEntity<Ncc> getNcc(@PathVariable("idncc") String idncc) {
        Ncc n = nccRepository.findById(idncc).get();

        return new ResponseEntity<Ncc>(n, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<Ncc>> getNcc2() {
        List<Ncc> list = nccRepository.get10ncc();
        return new ResponseEntity<List<Ncc>>(list, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Products>> getProduct(@RequestParam String idncc,
            @RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> size) {
        Page<Products> page = productDAO.getProductWithNcc(idncc,
                PageRequest.of(pageNumber.orElse(0), size.orElse(10)));
        return new ResponseEntity<Page<Products>>(page, HttpStatus.OK);
    }
}
