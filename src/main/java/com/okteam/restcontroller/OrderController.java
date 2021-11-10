package com.okteam.restcontroller;

import java.util.List;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.OrderRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.Orderdto;
import com.okteam.entity.Orders;
import com.okteam.entity.Details;

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


    // get
    @GetMapping("/get/{idorder}")
    public ResponseEntity<Orders> getNcc(@PathVariable("idorder") Integer idorder) {
        Orders o = oRepository.findById(idorder).get();

        return new ResponseEntity<Orders>(o, HttpStatus.OK);
    }

    // post
    @PostMapping
    public ResponseEntity<Orders> saveOrder(@RequestBody Orderdto orderdto) {
        Orders order = new Orders();
        if (!cRepository.existsById(orderdto.getIdctv())) {
            System.out.print("Không tìm thấy CTV");
            return new ResponseEntity<Orders>(order, HttpStatus.NOT_FOUND);
        } else if (!nRepository.existsById(orderdto.getIdncc())) {
            System.out.print("Không tìm thấy NCC");
            return new ResponseEntity<Orders>(order, HttpStatus.NOT_FOUND);
        } else {
            order.setDateorder(orderdto.getDateorder());
            order.setTotal(orderdto.getTotal());
            order.setStatus(orderdto.getStatus());
            order.setAddress(orderdto.getAddress());
            order.setCustomer(orderdto.getCustomer());
            order.setSdtcustomer(orderdto.getSdtcustomer());
            order.setPayment(orderdto.getPayment());

            order.setCtv(cRepository.findById(orderdto.getIdctv()).get());
            order.setNcc(nRepository.findById(orderdto.getIdncc()).get());
        }

        return new ResponseEntity<Orders>(oRepository.save(order), HttpStatus.OK);
    }

    // put
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable("id") Integer id, @RequestBody Orderdto orderdto) {
        Orders orders = oRepository.findById(id).get();
        if (!cRepository.existsById(orderdto.getIdctv())) {
            System.out.print("Không tìm thấy CTV");
            return new ResponseEntity<Orders>(orders, HttpStatus.NOT_FOUND);
        } else if (!nRepository.existsById(orderdto.getIdncc())) {
            System.out.print("Không tìm thấy NCC");
            return new ResponseEntity<Orders>(orders, HttpStatus.NOT_FOUND);
        } else {
            orders.setDateorder(orderdto.getDateorder());
            orders.setTotal(orderdto.getTotal());
            orders.setStatus(orderdto.getStatus());
            orders.setAddress(orderdto.getAddress());
            orders.setCustomer(orderdto.getCustomer());
            orders.setSdtcustomer(orderdto.getSdtcustomer());
            orders.setPayment(orderdto.getPayment());
    
            orders.setCtv(cRepository.findById(orderdto.getIdctv()).get());
            orders.setNcc(nRepository.findById(orderdto.getIdncc()).get());
        }

        return new ResponseEntity<Orders>(oRepository.save(orders), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public void deleteOrders(@PathVariable("id") Integer id) {
        if(!oRepository.existsById(id)){

        } else {
            Orders o = oRepository.findById(id).get();
            if(o.getStatus()==1){
                System.out.println("Hủy cmm à thanh toán rồi");
            }
            else{
                List<Details>list = o.getDetails();
                // oRepository.deleteAll(list);
                oRepository.deleteById(id);
            }
        }

        
    }
}