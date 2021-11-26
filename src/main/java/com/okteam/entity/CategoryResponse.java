package com.okteam.entity;

import java.util.List;

import lombok.Data;

@Data
public class CategoryResponse {
    Category category;
    List<Category> child;
    List<Brand> brands;
}