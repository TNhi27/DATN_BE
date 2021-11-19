package com.okteam.restcontroller;

import java.util.Date;
import java.util.List;

import com.okteam.dao.DetailsRepository;
import com.okteam.dao.FollowSellRepository;
import com.okteam.dao.OrderRepository;
import com.okteam.entity.Details;
import com.okteam.entity.FollowSell;
import com.okteam.entity.Orders;
import com.okteam.entity.ReportFollow;
import com.okteam.entity.ReportbyDay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    //get hóa đơn theo ngày
    @GetMapping("/getorder")
    public ResponseEntity<ReportbyDay> getreport(@RequestParam int d,@RequestParam int m,@RequestParam int y){
        ReportbyDay list=orderRepository.getReportOrdersByDay1(d,m,y);
        return new ResponseEntity<ReportbyDay>(list,HttpStatus.OK);
    }
    //lấy hóa đơn theo tháng
    @GetMapping("/getmonth")
    public ResponseEntity<List<ReportbyDay>> getreportmonth(@RequestParam int y){
        List<ReportbyDay> list=orderRepository.getReportOrdersByMonth(y);
        return new ResponseEntity<List<ReportbyDay>>(list,HttpStatus.OK);
    }
    //thống kê hóa đơn chưa xác nhận theo ngày
    @GetMapping("/getdelayday")
    public ResponseEntity<ReportbyDay> getorderdelaybyday(@RequestParam int d,@RequestParam int m,@RequestParam int y){
        ReportbyDay list=orderRepository.getOrderDelayByDay(d, m, y);
        return new ResponseEntity<ReportbyDay>(list,HttpStatus.OK);
    }
    //thống kê hóa đơn chờ xác nhận theo ngày
    @GetMapping("/getwaitingday")
    public ResponseEntity<ReportbyDay> getorderwaitingbyday(@RequestParam int d,@RequestParam int m,@RequestParam int y){
        ReportbyDay list=orderRepository.getOrderWaitingByDay(d, m, y);
        return new ResponseEntity<ReportbyDay>(list,HttpStatus.OK);
    }
    //thống kê hóa đơn đang giao theo ngày
    @GetMapping("/getshipping")
    public ResponseEntity<ReportbyDay> getshipping(@RequestParam int d,@RequestParam int m,@RequestParam int y){
        ReportbyDay list=orderRepository.getShipping(d, m, y);
        return new ResponseEntity<ReportbyDay>(list,HttpStatus.OK);
    }
    //thống kê đơn bị hủy theo ngày
    @GetMapping("/getcancelday")
    public ResponseEntity<ReportbyDay> getcancelday(@RequestParam int d,@RequestParam int m,@RequestParam int y){
        ReportbyDay list=orderRepository.getCancelByDate(d, m, y);
        return new ResponseEntity<ReportbyDay>(list,HttpStatus.OK);
    }//thống kê lượt follow theo ngày
    @GetMapping("/getfollow")
    public ResponseEntity<ReportFollow> getfollow(@RequestParam int d,@RequestParam int m,@RequestParam int y){
        ReportFollow list=followsellRespository.getReportFollow(d, m, y);
        return new ResponseEntity<ReportFollow>(list,HttpStatus.OK);
    }
   
}