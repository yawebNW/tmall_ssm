package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.bean.Order;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 20:24
 **/
@Controller
@RequestMapping("")
public class OrderController {
  @Autowired
  private OrderService orderService;
  @RequestMapping("admin_order_list")
  public String list(Page page, Model model) {
    PageHelper.offsetPage(page.getStart(), page.getCount());
    PageHelper.orderBy("id desc");
    List<Order> orders = orderService.list();
    int total = (int) new PageInfo<>(orders).getTotal();
    page.setTotal(total);
    model.addAttribute("page", page);
    model.addAttribute("os",orders);
    return "admin/listOrder";
  }

  @RequestMapping("admin_order_delivery")
  public String delivery(Order order) {
    order.setDeliveryDate(new Date());
    order.setStatus(OrderService.waitConfirm);
    orderService.update(order);
    return "redirect:admin_order_list";
  }
}
