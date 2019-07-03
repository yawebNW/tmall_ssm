package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.bean.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.PropertyService;
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
 * @date 2019/7/2 21:09
 **/
@Controller
@RequestMapping("")
public class PropertyController {
  @Autowired
  PropertyService service;
  @Autowired
  CategoryService categoryService;

  @RequestMapping("admin_property_list")
  public String list(Integer cid, Page page,Model model) {
    PageHelper.offsetPage(page.getStart(), page.getCount());
    PageHelper.orderBy("id desc");
    List<Property> ps = service.list(cid);
    int total = (int) new PageInfo<>(ps).getTotal();
    page.setTotal(total);
    page.setParam("&cid="+cid);
    model.addAttribute("ps",ps);
    model.addAttribute("page",page);
    model.addAttribute("c",categoryService.get(cid));
    return "admin/listProperty";
  }

  @RequestMapping("admin_property_add")
  public String add(Property property) {
    service.add(property);
    return "redirect:/admin_property_list?cid="+property.getCid();
  }

  @RequestMapping("admin_property_delete")
  public String delete(Property property) {
    property = service.get(property.getId());
    service.delete(property);
    return "redirect:/admin_property_list?cid="+property.getCid();
  }

  @RequestMapping("admin_property_edit")
  public String edit(Property property, Model model) {
    property = service.get(property.getId());
    property.setCategory(categoryService.get(property.getCid()));
    model.addAttribute("p",property);
    return "admin/editProperty";
  }

  @RequestMapping("admin_property_update")
  public String update(Property property) {
    service.update(property);
    return "redirect:admin_property_list?cid="+property.getCid();
  }
}
