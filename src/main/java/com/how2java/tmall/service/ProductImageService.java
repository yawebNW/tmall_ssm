package com.how2java.tmall.service;

import com.how2java.tmall.bean.Product;
import com.how2java.tmall.bean.ProductImage;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 10:51
 **/

public interface ProductImageService {
  String type_single = "type_single";
  String type_detail = "type_detail";
  void delete(ProductImage productImage);
  List<ProductImage> listSingle(int pid);
  List<ProductImage> listDetail(int pid);
  void add(ProductImage productImage);
  ProductImage get(int id);
}
