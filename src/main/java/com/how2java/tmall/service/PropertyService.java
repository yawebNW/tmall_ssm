package com.how2java.tmall.service;

import com.how2java.tmall.bean.Property;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/2 20:59
 **/

public interface PropertyService {
  void add(Property property);
  void update(Property property);
  void delete(Property property);
  List<Property> list(int cid);
  Property get(int id);
}
