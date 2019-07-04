package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.Order;
import com.how2java.tmall.bean.OrderItem;
import com.how2java.tmall.bean.OrderItemExample;
import com.how2java.tmall.dao.OrderItemMapper;
import com.how2java.tmall.dao.ProductMapper;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 17:05
 **/
@Service
public class OrderItemServiceImpl implements OrderItemService {
  @Autowired
  private OrderItemMapper orderItemMapper;
  @Autowired
  private ProductService productService;
  @Override
  public void add(OrderItem orderItem) {
    orderItemMapper.insert(orderItem);
  }

  @Override
  public void delete(int id) {
    orderItemMapper.deleteByPrimaryKey(id);
  }

  @Override
  public void update(OrderItem orderItem) {
    orderItemMapper.updateByPrimaryKeySelective(orderItem);
  }

  @Override
  public OrderItem get(int id) {
    OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
    setProduct(orderItem);
    return orderItem;
  }

  @Override
  public List<OrderItem> list(int oid) {
    OrderItemExample orderItemExample = new OrderItemExample();
    orderItemExample.createCriteria().andOidEqualTo(oid);
    List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
    setProduct(orderItems);
    return orderItems;
  }

  @Override
  public int getSaleCount(int pid) {
    OrderItemExample orderItemExample = new OrderItemExample();
    orderItemExample.createCriteria().andPidEqualTo(pid);
    List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
    int saleCount = 0;
    for (OrderItem orderItem : orderItems) {
      saleCount+=orderItem.getNumber();
    }
    return saleCount;
  }

  @Override
  public List<OrderItem> listByUser(int uid) {
    OrderItemExample orderItemExample = new OrderItemExample();
    orderItemExample.createCriteria().andUidEqualTo(uid).andOidIsNull();
    List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
    setProduct(orderItems);
    return orderItems;
  }

  @Override
  public OrderItem getByProductAndUser(int pid, int uid) {
    OrderItemExample orderItemExample = new OrderItemExample();
    orderItemExample.createCriteria().andPidEqualTo(pid).andUidEqualTo(uid);
    List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
    if (orderItems.isEmpty()) {
      return null;
    }
    setProduct(orderItems.get(0));
    return orderItems.get(0);
  }

  @Override
  public void fillOrder(Order order) {
    List<OrderItem> orderItems = list(order.getId());
    float total = 0;
    int totalNumber = 0;
    for (OrderItem orderItem : orderItems) {
      total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
      totalNumber+=orderItem.getNumber();
    }
    order.setOrderItems(orderItems);
    order.setTotalNumber(totalNumber);
    order.setTotal(total);
  }

  @Override
  public void fillOrder(List<Order> orders) {
    for (Order order : orders) {
      fillOrder(order);
    }
  }

  private void setProduct(OrderItem orderItem) {
    orderItem.setProduct(productService.get(orderItem.getPid()));
  }

  private void setProduct(List<OrderItem> orderItems) {
    for (OrderItem orderItem : orderItems) {
      setProduct(orderItem);
    }
  }


}
