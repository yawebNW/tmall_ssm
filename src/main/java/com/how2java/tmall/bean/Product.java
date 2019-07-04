package com.how2java.tmall.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class Product {
  @Getter @Setter private Integer id;
  @Getter private String name;
  @Getter private String subTitle;
  @Getter @Setter private Float originalPrice;
  @Getter @Setter private Float promotePrice;
  @Getter @Setter private Integer stock;
  @Getter @Setter private Integer cid;
  @Getter @Setter private Date createDate;
  @Getter private Category category;
  @Getter @Setter private ProductImage firstProductImage;
  @Getter @Setter private List<ProductImage> productSingleImages;
  @Getter @Setter private List<ProductImage> productDetailImages;
  @Getter @Setter private int saleCount;
  @Getter @Setter private int reviewCount;

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle == null ? null : subTitle.trim();
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}