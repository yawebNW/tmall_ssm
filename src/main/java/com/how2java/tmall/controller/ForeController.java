package com.how2java.tmall.controller;

import com.how2java.tmall.bean.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 21:07
 **/
@Controller
@RequestMapping("")
public class ForeController {
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private ProductService productService;

  @RequestMapping("forehome")
  public String home(HttpSession session, Model model) {
    List<Category> cs = categoryService.list();
    productService.fillCategory(cs);
    model.addAttribute("cs",cs);
    return "fore/home";
  }
}
