package com.how2java.tmall.service;

import com.how2java.tmall.bean.Order;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 17:04
 **/

public interface OrderService {
  String waitPay = "waitPay";
  String waitDelivery = "waitDelivery";
  String waitConfirm = "waitConfirm";
  String waitReview = "waitReview";
  String finish = "finish";
  String delete = "delete";

  void add(Order order);
  void delete(Order order);
  void update(Order order);
  Order get(int id);
  List<Order> list();
}
