package com.how2java.tmall.service;

import com.how2java.tmall.bean.Order;
import com.how2java.tmall.bean.OrderItem;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 17:04
 **/

public interface OrderItemService {
  void add(OrderItem order);
  void delete(int id);
  void update(OrderItem order);
  OrderItem get(int id);
  List<OrderItem> list(int oid);
  int getSaleCount(int pid);
  List<OrderItem> listByUser(int uid);
  OrderItem getByProductAndUser(int pid, int uid);
  void fillOrder(Order order);
  void fillOrder(List<Order> orders);
}
