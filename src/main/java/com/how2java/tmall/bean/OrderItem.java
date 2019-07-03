package com.how2java.tmall.bean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItem {
    private Integer id;
    private Integer pid;
    private Integer oid;
    private Integer uid;
    private Integer number;
    private Product product;
}