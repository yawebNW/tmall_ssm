package com.how2java.tmall.service;

import com.how2java.tmall.bean.Category;
import com.how2java.tmall.bean.Product;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/2 22:07
 **/

public interface ProductService {
  List<Product> list(int cid);
  Product get(int id);
  void add(Product product);
  void update(Product product);
  void delete(int id);
  void fillCategory(Category category);
  void fillCategory(List<Category> categories);
  void setImages(Product product);
  List<Product> search(String keyword);
  void setSaleAndReviewCount(List<Product> products);
  void setSaleAndReviewcount(Product product);
}
