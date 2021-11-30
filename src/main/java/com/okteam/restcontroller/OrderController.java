package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.DetailsRepository;
import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.OrderRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.DetailsDTO;
import com.okteam.dto.Orderdto;
import com.okteam.dto.OrdersRequest;
import com.okteam.dto.RegiProductsDTO;
import com.okteam.entity.Orders;
import com.okteam.entity.Products;
import com.okteam.entity.RegiProducts;

import com.okteam.entity.Response;
import com.okteam.exception.NotEnoughMoney;
import com.okteam.exception.NotFoundSomething;
import com.okteam.entity.Ctv;
import com.okteam.entity.Details;
import com.okteam.entity.Ncc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Autowired
    FollowSellRepository fdao;

    // get
    @GetMapping("/get/{idorder}")
    public ResponseEntity<Orders> getNcc(@PathVariable("idorder") Integer idorder) {
        Orders o = oRepository.findById(idorder).orElseThrow(() -> new NotFoundSomething(":( Khong tim thay don hag"));

        return new ResponseEntity<Orders>(o, HttpStatus.OK);
    }

    @GetMapping("/ctv/{ctv}")
    public ResponseEntity<Page<Orders>> selectOrdersWithCtv(@PathVariable("ctv") String ctv,
            @RequestParam Optional<String> status, @RequestParam Optional<Integer> id) {
        Sort sort = Sort.by("dateorder").descending();

        if (id.orElse(-1) <= 0) {
            Page<Orders> page = oRepository.getOrdersWithCtvStatus(ctv, status.orElse("%%"),
                    PageRequest.of(0, 20, sort));
            return new ResponseEntity<Page<Orders>>(page, HttpStatus.OK);
        } else {
            Page<Orders> page = oRepository.getOrdersWithCtvId(ctv, id.orElse(0), PageRequest.of(0, 20, sort));
            return new ResponseEntity<Page<Orders>>(page, HttpStatus.OK);
        }

    }

    @GetMapping("/ncc/{ncc}")
    public ResponseEntity<Page<Orders>> selectOrdersWithNcc(@PathVariable("ncc") String ncc,
            @RequestParam Optional<String> status, @RequestParam Optional<Integer> id) {
        Sort sort = Sort.by("dateorder").descending();

        if (id.orElse(-1) <= 0) {
            Page<Orders> page = oRepository.getOrdersWithNccStatus(ncc, status.orElse("%%"),
                    PageRequest.of(0, 20, sort));
            return new ResponseEntity<Page<Orders>>(page, HttpStatus.OK);
        } else {
            Page<Orders> page = oRepository.getOrdersWithNccId(ncc, id.orElse(0), PageRequest.of(0, 20, sort));
            return new ResponseEntity<Page<Orders>>(page, HttpStatus.OK);
        }

    }

    // post
    @PostMapping
    public ResponseEntity<Orders> saveOrder(@RequestBody OrdersRequest orderdto) {

        Orders order = new Orders();

        order.setDateorder(new Date());

        order.setStatus(0);
        order.setAddress(orderdto.getAddress());
        order.setCustomer(orderdto.getCustomer());
        order.setSdtcustomer(orderdto.getSdtcustomer());
        order.setPayment(orderdto.getPayment());
        order.setTotal(orderdto.getTotal());
        order.setHuyen(orderdto.getHuyen());
        order.setXa(orderdto.getXa());

        // lay ctv hien tai
        var username_ctv = SecurityContextHolder.getContext().getAuthentication().getName();
        Ctv ctv = cRepository.findById(username_ctv).get();

        if (ctv.getMoney() < orderdto.getTotal()) {
            throw new NotEnoughMoney();
        } else {
            ctv.setMoney(ctv.getMoney() - orderdto.getTotal());
            cRepository.save(ctv);
        }
        order.setCtv(ctv);

        order.setNcc(nRepository.findById(orderdto.getIdncc()).orElseThrow(() -> new NotFoundSomething(":(")));

        Orders rsOrder = oRepository.save(order);

        List<Details> details = new ArrayList<>();

        for (int i = 0; i < orderdto.getDetails().size(); i++) {
            Products products = pRepository.findById(orderdto.getDetails().get(i).getIdpro()).get();
            Details d = new Details();
            d.setQty(orderdto.getDetails().get(i).getSl());
            d.setOrders(rsOrder);
            d.setProducts(products);
            d.setRevenue(orderdto.getDetails().get(i).getPrice());
            details.add(d);
            detaildao.save(d);
        }

        return new ResponseEntity<Orders>(oRepository.findById(rsOrder.getIdorder()).get(), HttpStatus.OK);
    }

    @PostMapping("/update_ghn_code")
    public ResponseEntity<Orders> update_ghn(@RequestParam Optional<Integer> id, @RequestParam String code) {
        Orders o = oRepository.findById(id.get()).orElseThrow(() -> new NotFoundSomething("Khong tim thay don hang"));
        o.setOrder_code(code);
        o.setStatus(2);
        oRepository.save(o);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Object> setStatus(@PathVariable("id") int id) {
        String idctv = SecurityContextHolder.getContext().getAuthentication().getName();
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething("Đơn không tồn tại"));

        Ctv ctv = cRepository.findById(idctv).get();

        if (o.getStatus() == 0) {
            ctv.setMoney(ctv.getMoney() + o.getTotal());
            cRepository.save(ctv);
            oRepository.deleteById(id);
        } else {
            return new ResponseEntity<Object>("Khong the xoa", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>("Xoa thanh cong", HttpStatus.OK);
    }

    @PostMapping("/cancelncc/{id}")
    public ResponseEntity<Object> setStatusncc(@PathVariable("id") int id) {
        String ncc = SecurityContextHolder.getContext().getAuthentication().getName();
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething("Đơn không tồn tại"));

        Ctv ctv = o.getCtv();

        if (o.getStatus() == 0) {
            o.setStatus(4);
            ctv.setMoney(ctv.getMoney() + o.getTotal());
            cRepository.save(ctv);
        } else {
            return new ResponseEntity<Object>(oRepository.save(o), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(oRepository.save(o), HttpStatus.OK);
    }

    // put
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable("id") Integer id, @RequestBody Orderdto orderdto) {
        Orders orders = oRepository.findById(id).get();
        if (!nRepository.existsById(orderdto.getIdncc())) {
            System.out.print("Không tìm thấy NCC");
            return new ResponseEntity<Orders>(orders, HttpStatus.NOT_FOUND);
        } else {

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
    public Response<String> deleteOrders(@PathVariable("id") Integer id) {
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething(":(Khong tim thay don hang"));

        if (o.getStatus() == 0) {
            oRepository.deleteById(id);
            return new Response<>(null, null, "Xoa thanh cong");
        }

        return new Response<>(null, null, "Khong the xoa");

    }

    @PostMapping("/update_status")
    public ResponseEntity<Orders> updatestatus(@RequestParam int id, @RequestParam int status) {
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething(":( Khong timthay don hang"));
        if (status != 5) {

            o.setStatus(status);
        }

        return new ResponseEntity<Orders>(oRepository.save(o), HttpStatus.OK);

    }

    @PostMapping("/pay_to_ctv")
    public ResponseEntity<Orders> payctv(@RequestParam int id) {
        Orders o = oRepository.findById(id).orElseThrow(() -> new NotFoundSomething(":( Khong timthay don hang"));
        Ncc ncc = o.getNcc();
        Ctv ctv = o.getCtv();

        ctv.setMoney(ctv.getMoney() + o.getPayment());

        if (o.getStatus() == 1) {
            ncc.setMoney(ncc.getMoney() + o.getTotal());
        }

        nRepository.save(ncc);
        cRepository.save(ctv);

        o.setDatefinish(new Date());
        o.setStatus(5);
        return new ResponseEntity<Orders>(oRepository.save(o), HttpStatus.OK);

    }

}