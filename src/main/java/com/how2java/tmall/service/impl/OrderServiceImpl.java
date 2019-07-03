package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.Order;
import com.how2java.tmall.bean.OrderExample;
import com.how2java.tmall.bean.OrderItem;
import com.how2java.tmall.bean.User;
import com.how2java.tmall.dao.OrderMapper;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 17:04
 **/
@Service
public class OrderServiceImpl implements OrderService {
  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private OrderItemService orderItemService;
  @Autowired
  private UserService userService;
  @Override
  public void add(Order order) {
    orderMapper.insert(order);
  }

  @Override
  public void delete(Order order) {
    order.setStatus(delete);
    orderMapper.updateByPrimaryKeySelective(order);
  }

  @Override
  public void update(Order order) {
    orderMapper.updateByPrimaryKeySelective(order);
  }

  @Override
  public Order get(int id) {
    Order order = orderMapper.selectByPrimaryKey(id);
    fill(order);
    return order;
  }

  @Override
  public List<Order> list() {
    List<Order> orders = orderMapper.selectByExample(new OrderExample());
    fill(orders);
    return orders;
  }

  private void fill(List<Order> orders) {
    for (Order order : orders) {
      fill(order);
    }
  }

  private void fill(Order order) {
    List<OrderItem> orderItems = orderItemService.list(order.getId());
    float total = 0;
    int totalNumber =0 ;
    for (OrderItem orderItem : orderItems) {
      total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
      totalNumber+=orderItem.getNumber();
    }
    order.setOrderItems(orderItems);
    order.setTotal(total);
    order.setTotalNumber(totalNumber);
    order.setUser(userService.get(order.getUid()));
  }
}
