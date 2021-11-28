package com.okteam.restcontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.okteam.dao.CtvRepository;
import com.okteam.dao.DetailsRepository;
import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.OrderRepository;
import com.okteam.dto.ReportResponseCtv;
import com.okteam.entity.Ctv;
import com.okteam.entity.Details;
import com.okteam.entity.FollowSell;
import com.okteam.entity.Orders;
import com.okteam.entity.ReportFollow;
import com.okteam.entity.ReportbyDay;

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

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/report_ctv")
public class ReportCTV {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DetailsRepository detailsRepository;
    @Autowired
    FollowSellRepository followsellRespository;
    @Autowired
    CtvRepository ctvRepository;

    @PostMapping
    public ResponseEntity<ReportResponseCtv> report_ctvs(@RequestParam int d, @RequestParam int m,
            @RequestParam int y) {
        String ctv = SecurityContextHolder.getContext().getAuthentication().getName();
        ReportbyDay byDay = orderRepository.getReportOrdersByDay1(d, m, y, ctv);
        ReportResponseCtv report = new ReportResponseCtv();
        report.setCount_order_d(byDay.getOrder());
        report.setCount_products_d(byDay.getQty());
        report.setTotal_d(byDay.getTotal());

        List<ReportbyDay> byStatus = orderRepository.getOrderWithStatus(d, m, y, ctv);

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
        Ctv ctv_root = ctvRepository.findById(ctv).get();
        List<ReportbyDay> listrp = orderRepository.getReportOrdersByMonth(y, ctv);
        report.setMoney(ctv_root.getMoney());
        List<Orders> or = ctv_root.getOrders();
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
        report.setCount_orders(ctv_root.getOrders().size());

        return new ResponseEntity<ReportResponseCtv>(report, HttpStatus.OK);
    }

    @GetMapping("/doanh_thu")
    public Long getdoanhthu(@RequestParam int m, @RequestParam int y, @RequestParam String ctv) {
        Long dt = orderRepository.getDoanhthuThang(ctv, m, y);
        return dt;
    }

    // get hóa đơn theo ngày
    @GetMapping("/getorder")
    public ResponseEntity<List<ReportbyDay>> getreport() {
        List<ReportbyDay> list = orderRepository.getOrderWithStatus(15, 11, 2021, "leo10");
        return new ResponseEntity<List<ReportbyDay>>(list, HttpStatus.OK);
    }

    // lấy hóa đơn theo tháng
    @GetMapping("/getmonth")
    public ResponseEntity<List<ReportbyDay>> getreportmonth(@RequestParam int y) {
        List<ReportbyDay> list = orderRepository.getOrderWithStatus(15, 11, 2021, "leo10");
        return new ResponseEntity<List<ReportbyDay>>(list, HttpStatus.OK);
    }

    // thống kê hóa đơn chưa xác nhận theo ngày
    // @GetMapping("/getdelayday")
    // public ResponseEntity<ReportbyDay> getorderdelaybyday(@RequestParam int d,
    // @RequestParam int m,
    // @RequestParam int y) {
    // ReportbyDay list = orderRepository.getOrderDelayByDay(d, m, y);
    // return new ResponseEntity<ReportbyDay>(list, HttpStatus.OK);
    // }

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