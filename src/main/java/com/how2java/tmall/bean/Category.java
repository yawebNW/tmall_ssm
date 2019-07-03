package com.how2java.tmall.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Category {
    @Setter private Integer id;
    private String name;
    @Setter List<Product> products;
    @Setter List<List<Product>> productsByRow;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}