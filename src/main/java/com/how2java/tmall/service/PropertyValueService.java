package com.how2java.tmall.service;

import com.how2java.tmall.bean.Product;
import com.how2java.tmall.bean.PropertyValue;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 14:51
 **/

public interface PropertyValueService {
  List<PropertyValue> list(int pid);
  PropertyValue get(int ptid, int pid);
  void update(PropertyValue propertyValue);
  void init(Product product);
}
