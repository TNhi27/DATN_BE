package com.okteam.restcontroller;

import java.util.List;

import com.okteam.dao.NewsReponsitory;
import com.okteam.entity.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/news")
public class NewsController {

    @Autowired
    NewsReponsitory dao;

    @GetMapping
    public ResponseEntity<Page<Post>> get() {
        Page<Post> rs = dao.findAll(PageRequest.of(0, 8, Sort.by("createdate")));
        return new ResponseEntity<Page<Post>>(rs, HttpStatus.OK);
    }
}