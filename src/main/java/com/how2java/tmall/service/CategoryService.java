package com.how2java.tmall.service;

import com.how2java.tmall.bean.Category;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/1 20:06
 **/

public interface CategoryService {

  int add(Category category);

  int update(Category category);

  void delete(Category category);

  Category get(int id);

  List<Category> list();
}
