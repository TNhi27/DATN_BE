package com.okteam.restcontroller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.okteam.dao.TransactionRepository;
import com.okteam.entity.Transaction;
import com.okteam.utils.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionRepository tdao;
    @Autowired
    RegisterService registerService;

    @PostMapping("/get")
    public ResponseEntity<Page<Transaction>> get(@RequestParam Optional<String> type,
            @RequestParam Optional<String> done) {
        Sort s = Sort.by("createdate").descending();
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        Page page = tdao.getTransaction(type.orElse("%%"), name, PageRequest.of(0, 100, s));

        return new ResponseEntity<Page<Transaction>>(page, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> create(@RequestParam Integer value, @RequestParam Integer type,
            @RequestParam Optional<String> paypal) {
        Transaction tr = new Transaction();
        tr.setCreatedate(new Date());
        tr.setDone(0);
        tr.setIdpaypal(paypal.orElse(""));
        tr.setNote("");
        tr.setType(type);
        tr.setValue(value);
        tr.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return new ResponseEntity<Transaction>(tdao.save(tr), HttpStatus.OK);
    }
}