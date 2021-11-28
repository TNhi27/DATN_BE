package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.okteam.dao.DetailsRepository;
import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.NccRepository;
import com.okteam.dao.OrderRepository;
import com.okteam.dao.ProductRepository;
import com.okteam.dto.ReportResponseNcc;
import com.okteam.entity.Details;
import com.okteam.entity.FollowSell;
import com.okteam.entity.Ncc;
import com.okteam.entity.Orders;
import com.okteam.entity.ProductGroup;
import com.okteam.entity.Products;
import com.okteam.entity.ReportFollow;
import com.okteam.entity.ReportbyDay;
import com.okteam.utils.ConvertDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.var;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/report_ncc")
public class ReportNCC {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DetailsRepository detailsRepository;
    @Autowired
    FollowSellRepository followsellRespository;
    @Autowired
    NccRepository nccRepository;
    @Autowired
    ProductRepository productRepository;

    // get hóa đơn theo ngày
    @GetMapping("/getorder")
    public ResponseEntity<ReportbyDay> getreport(@RequestParam int d, @RequestParam int m, @RequestParam int y) {
        ReportbyDay list = orderRepository.getReportOrdersByDay1(d, m, y, "leo10");
        return new ResponseEntity<ReportbyDay>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReportResponseNcc> report_ctvs(@RequestParam int d, @RequestParam int m,
            @RequestParam int y) {
        String ncc = SecurityContextHolder.getContext().getAuthentication().getName();
        ReportbyDay byDay = orderRepository.getReportOrdersByDay2(d, m, y, ncc);
        ReportResponseNcc report = new ReportResponseNcc();

        report.setCount_products_d(byDay.getQty());
        report.setTotal_d(byDay.getTotal());

        List<ReportbyDay> byStatus = orderRepository.getOrderWithStatusNcc(d, m, y, ncc);

        for (int i = 0; i < byStatus.size(); i++) {
            int id = Integer.parseInt(byStatus.get(i).getId().toString());
            if (id == 0) {
                report.setCount_order0(byStatus.get(i).getOrder());
            }
            if (id == 1) {
                report.setCount_order1(byStatus.get(i).getOrder());
            }
            if (id == 2) {
                report.setCount_order2(byStatus.get(i).getOrder());
            }
            if (id == 3) {
                report.setCount_order3(byStatus.get(i).getOrder());
            }
            if (id == 4) {
                report.setCount_order4(byStatus.get(i).getOrder());
            }
        }

        report.setCount_order_d(report.getCount_order0() + report.getCount_order1() + report.getCount_order2()
                + report.getCount_order3() + report.getCount_order4());

        Ncc ncc_root = nccRepository.findById(ncc).get();
        List<ReportbyDay> listrp = orderRepository.getReportOrdersByMonthNcc(y, ncc);
        report.setMoney(ncc_root.getMoney());
        List<Orders> or = ncc_root.getOrders();
        Collections.sort(or, new Comparator<Orders>() {
            @Override
            public int compare(Orders o1, Orders o2) {
                if (o1.getTotal() < o2.getTotal()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        List<Orders> listod = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listod.add(or.get(i));
        }
        report.setList_orders(listod);
        report.setList_report(listrp);
        report.setCount_orders(ncc_root.getOrders().size());
        report.setCount_products(ncc_root.getProducts().size());
        report.setList_ctv(ncc_root.getFollowSell().stream().map((e) -> e.getFl_ctv()).collect(Collectors.toList()));

        report.setList_ctv_d(ncc_root.getFollowSell().stream()
                .filter((e) -> ConvertDate.getDay(e.getDate()) == d && ConvertDate.getMonth(e.getDate()) == m
                        && ConvertDate.getYear(e.getDate()) == y)
                .map((e) -> e.getFl_ctv()).collect(Collectors.toList()));

        List<ProductGroup> details = orderRepository.getProductGroups("congvinh");
        details.sort((e1, e2) -> {
            if (e1.getNum_sell() >= e2.getNum_sell()) {
                return -1;
            } else {
                return 1;
            }
        });

        report.setList_product(details);

        return new ResponseEntity<ReportResponseNcc>(report, HttpStatus.OK);
    }

    @GetMapping("/doanh_thu")
    public Long getdoanhthu(@RequestParam int m, @RequestParam int y, @RequestParam String ncc) {
        Long dt = orderRepository.getDoanhthuThangNcc(ncc, m, y);
        return dt;
    }

    // thống kê hóa đơn chờ xác nhận theo ngày
    @GetMapping("/getwaitingday")
    public ResponseEntity<ReportbyDay> getorderwaitingbyday(@RequestParam int d, @RequestParam int m,
            @RequestParam int y) {
        ReportbyDay list = orderRepository.getOrderWaitingByDay(d, m, y);
        return new ResponseEntity<ReportbyDay>(list, HttpStatus.OK);
    }

    // thống kê hóa đơn đang giao theo ngày
    @GetMapping("/getshipping")
    public ResponseEntity<ReportbyDay> getshipping(@RequestParam int d, @RequestParam int m, @RequestParam int y) {
        ReportbyDay list = orderRepository.getShipping(d, m, y);
        return new ResponseEntity<ReportbyDay>(list, HttpStatus.OK);
    }

    // thống kê đơn bị hủy theo ngày
    @GetMapping("/getcancelday")
    public ResponseEntity<ReportbyDay> getcancelday(@RequestParam int d, @RequestParam int m, @RequestParam int y) {
        ReportbyDay list = orderRepository.getCancelByDate(d, m, y);
        return new ResponseEntity<ReportbyDay>(list, HttpStatus.OK);
    }// thống kê lượt follow theo ngày

    @GetMapping("/getfollow")
    public ResponseEntity<ReportFollow> getfollow(@RequestParam int d, @RequestParam int m, @RequestParam int y) {
        ReportFollow list = followsellRespository.getReportFollow(d, m, y);
        return new ResponseEntity<ReportFollow>(list, HttpStatus.OK);
    }

}