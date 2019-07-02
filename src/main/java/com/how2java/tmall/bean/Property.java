package com.how2java.tmall.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author li
 */
public class Property {
    @Getter @Setter private Integer id;

    @Getter @Setter private Integer cid;

    @Getter private String name;

    @Getter @Setter private Category category;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}