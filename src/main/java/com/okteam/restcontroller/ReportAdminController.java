package com.okteam.restcontroller;

import java.util.List;
import java.util.Optional;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.OrderRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dao.TransactionRepository;
import com.okteam.dto.ReportAdmin;
import com.okteam.entity.ReportbyDay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
@RequestMapping("/report")
public class ReportAdminController {
    @Autowired
    NccRepository nccRepository;
    @Autowired
    CtvRepository ctvRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<ReportAdmin> getMethodName(@RequestParam int d, @RequestParam int m,
            @RequestParam Optional<Integer> year) {

        ReportAdmin rp = new ReportAdmin();

        // người dùng
        List<Object> rsncc = nccRepository.getReportCountUserInYear(year.orElse(2021));
        List<Object> rsctv = ctvRepository.getReportCountUserInYear(year.orElse(2021));
        rp.setTotal_ncc(nccRepository.count());
        rp.setTotal_ctv(ctvRepository.count());
        rp.setRp_ncc(rsncc);
        rp.setRp_ctv(rsctv);

        // sản phẩm
        rp.setTotal_sp(productRepository.count());
        rp.setRp_products(orderRepository.getProductGroupsAdmin());

        // đơn hàng
        List<ReportbyDay> byStatus = orderRepository.getOrderWithStatusAdmin(d, m, year.orElse(2021));
        rp.setTotal_order(orderRepository.count());
        for (int i = 0; i < byStatus.size(); i++) {
            int id = Integer.parseInt(byStatus.get(i).getId().toString());
            if (id == 0) {
                rp.setCount_order0(byStatus.get(i).getOrder());
            }
            if (id == 1) {
                rp.setCount_order1(byStatus.get(i).getOrder());
            }
            if (id == 2) {
                rp.setCount_order2(byStatus.get(i).getOrder());
            }
            if (id == 3) {
                rp.setCount_order3(byStatus.get(i).getOrder());
            }
            if (id == 4) {
                rp.setCount_order4(byStatus.get(i).getOrder());
            }
            if (id == 5) {
                rp.setCount_order5(byStatus.get(i).getOrder());
            }
        }

        // giao dịch
        rp.setRp_gd(transactionRepository.getGiaoDichGroup());

        return new ResponseEntity<ReportAdmin>(rp, HttpStatus.OK);
    }

}