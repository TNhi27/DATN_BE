package com.okteam.restcontroller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.okteam.dao.TransactionRepository;
import com.okteam.entity.Transaction;

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

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionRepository tdao;

    @PostMapping("/get")
    public ResponseEntity<Page<Transaction>> get(@RequestParam Optional<String> type,
            @RequestParam Optional<String> done) {
        Sort s = Sort.by("createdate").descending();
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        Page page = tdao.getTransaction(type.orElse("%%"), name, PageRequest.of(0, 100, s));

        return new ResponseEntity<Page<Transaction>>(page, HttpStatus.OK);
    }
}