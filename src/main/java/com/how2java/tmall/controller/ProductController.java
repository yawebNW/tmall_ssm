package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.bean.Category;
import com.how2java.tmall.bean.Product;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
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
 * @date 2019/7/2 22:07
 **/
@Controller
@RequestMapping("")
public class ProductController {
  @Autowired
  private ProductService productService;
  @Autowired
  private CategoryService categoryService;

  @RequestMapping("admin_product_list")
  public String list(Integer cid, Page page, Model model) {
    PageHelper.offsetPage(page.getStart(), page.getCount());
    PageHelper.orderBy("id desc");
    List<Product> ps = productService.list(cid);
    int total = (int) new PageInfo<>(ps).getTotal();
    page.setTotal(total);
    page.setParam("&cid="+cid);
    model.addAttribute("ps",ps);
    model.addAttribute("page",page);
    model.addAttribute("c",categoryService.get(cid));
    return "admin/listProduct";
  }

  @RequestMapping("admin_product_add")
  public String add(Product product) {
    productService.add(product);
    return "redirect:/admin_product_list?cid="+product.getCid();
  }
  @RequestMapping("admin_product_edit")
  public String edit(Product product, Model model) {
    product = productService.get(product.getId());
    model.addAttribute("p",product);
    return "admin/editProduct";
  }
  @RequestMapping("admin_product_update")
  public String update(Product product) {
    productService.update(product);
    return "redirect:/admin_product_list?cid="+product.getCid();
  }
  @RequestMapping("admin_product_delete")
  public String delete(Product product) {
    product = productService.get(product.getId());
    productService.delete(product.getId());
    return "redirect:/admin_product_list?cid="+product.getCid();
  }

}
