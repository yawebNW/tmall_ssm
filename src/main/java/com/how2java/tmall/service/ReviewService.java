package com.how2java.tmall.service;

import com.how2java.tmall.bean.Review;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/4 14:20
 **/

public interface ReviewService {
  void add(Review review);
  void update(Review review);
  void delete(int id);
  Review get(int id);
  List<Review> list(int pid);
}
