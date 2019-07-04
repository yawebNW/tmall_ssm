package com.how2java.tmall.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter @ToString
public class Review {
    @Setter private Integer id;
    private String content;
    @Setter private Integer uid;
    @Setter private Integer pid;
    @Setter private Date createDate;
    @Setter private User user;

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}