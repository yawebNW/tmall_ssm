package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.bean.Category;
import com.how2java.tmall.bean.User;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 16:53
 **/
@Controller
@RequestMapping("")
public class UserController {
  @Autowired
  private UserService userService;
  @RequestMapping("admin_user_list")
  public String list(Page page, Model model) {
    PageHelper.offsetPage(page.getStart(), page.getCount());
    PageHelper.orderBy("id desc");
    List<User> us = userService.list();
    int total = (int) new PageInfo<>(us).getTotal();
    page.setTotal(total);
    model.addAttribute("us", us);
    model.addAttribute("page", page);
    return "admin/listUser";
  }
}
