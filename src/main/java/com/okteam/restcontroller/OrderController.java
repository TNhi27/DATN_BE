package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.DetailsRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.OrderRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.DetailsDTO;
import com.okteam.dto.Orderdto;
import com.okteam.dto.RegiProductsDTO;
import com.okteam.entity.Orders;
import com.okteam.entity.Products;
import com.okteam.entity.RegiProducts;
import com.okteam.exception.NotFoundSomething;
import com.okteam.entity.Details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import javassist.NotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    OrderRepository oRepository;
    @Autowired
    ProductRepository pRepository;
    @Autowired
    CtvRepository cRepository;
    @Autowired
    NccRepository nRepository;
    @Autowired
    DetailsRepository detaildao;

    // get
    @GetMapping("/get/{idorder}")
    public ResponseEntity<Orders> getNcc(@PathVariable("idorder") Integer idorder) {
        Orders o = oRepository.findById(idorder).get();

        return new ResponseEntity<Orders>(o, HttpStatus.OK);
    }

    // post
    @PostMapping
    public ResponseEntity<Orders> saveOrder(@RequestBody Orderdto orderdto,
            @RequestParam Optional<List<RegiProductsDTO>> details) {

        Orders order = new Orders();

        order.setDateorder(new Date());

        order.setStatus(orderdto.getStatus());
        order.setAddress(orderdto.getAddress());
        order.setCustomer(orderdto.getCustomer());
        order.setSdtcustomer(orderdto.getSdtcustomer());
        order.setPayment(orderdto.getPayment());

        // lay ctv hien tai
        var ctv = SecurityContextHolder.getContext().getAuthentication().getName();
        order.setCtv(cRepository.findById(ctv).get());

        order.setNcc(nRepository.findById(orderdto.getIdncc()).orElseThrow(() -> new NotFoundSomething(":(")));

        Orders rsOrder = oRepository.save(order);
        int total = 0;
        int payment = 0;
        for (int i = 0; i < details.get().size(); i++) {
            Products products = pRepository.findById(details.get().get(i).getIdpro()).get();
            Details d = new Details();
            d.setQty(details.get().get(i).getSl());
            d.setOrders(rsOrder);
            d.setProducts(products);
            detaildao.save(d);
            total += d.getQty() * products.getPricectv();
            payment += d.getQty() * details.get().get(i).getPrice();
        }
        rsOrder.setTotal(total);
        rsOrder.setPayment(payment);
        return new ResponseEntity<Orders>(oRepository.save(rsOrder), HttpStatus.OK);
    }

    // put
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable("id") Integer id, @RequestBody Orderdto orderdto) {
        Orders orders = oRepository.findById(id).get();
        if (!nRepository.existsById(orderdto.getIdncc())) {
            System.out.print("Không tìm thấy NCC");
            return new ResponseEntity<Orders>(orders, HttpStatus.NOT_FOUND);
        } else {
            orders.setDateorder(orderdto.getDateorder());

            orders.setStatus(orderdto.getStatus());
            orders.setAddress(orderdto.getAddress());
            orders.setCustomer(orderdto.getCustomer());
            orders.setSdtcustomer(orderdto.getSdtcustomer());
            orders.setPayment(orderdto.getPayment());

            // orders.setCtv(cRepository.findById(orderdto.getIdctv()).get());
            orders.setNcc(nRepository.findById(orderdto.getIdncc()).get());
        }

        return new ResponseEntity<Orders>(oRepository.save(orders), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public void deleteOrders(@PathVariable("id") Integer id) {
        if (!oRepository.existsById(id)) {

        } else {
            Orders o = oRepository.findById(id).get();
            if (o.getStatus() == 1) {
                System.out.println("Hủy cmm à thanh toán rồi");
            } else {
                List<Details> list = o.getDetails();
                // oRepository.deleteAll(list);
                oRepository.deleteById(id);
            }
        }

    }
}