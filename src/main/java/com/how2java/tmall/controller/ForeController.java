package com.how2java.tmall.controller;

import com.how2java.tmall.bean.*;
import com.how2java.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

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
  @Autowired
  private UserService userService;
  @Autowired
  private OrderItemService orderItemService;
  @Autowired
  private ReviewService reviewService;
  @Autowired
  private PropertyValueService propertyValueService;

  @RequestMapping("forehome")
  public String home(Model model) {
    List<Category> cs = categoryService.list();
    productService.fillCategory(cs);
    model.addAttribute("cs",cs);
    return "fore/home";
  }

  @RequestMapping("foreregister")
  public String register(User user, Model model) {
    user.setName(HtmlUtils.htmlEscape(user.getName()));
    if (userService.isExist(user.getName())) {
      model.addAttribute("msg","用户名已经被使用,不能使用");
      model.addAttribute("user",null);
      return "fore/register";
    }
    userService.add(user);
    return "redirect:registerSuccessPage";
  }

  @RequestMapping("forelogin")
  public String login(User user, Model model, HttpSession session) {
    user.setName(HtmlUtils.htmlEscape(user.getName()));
    user = userService.get(user.getName(), user.getPassword());
    if (null == user) {
      model.addAttribute("msg","帐号密码错误");
      model.addAttribute("user",null);
      return "fore/login";
    }
    session.setAttribute("user", user);
    return "redirect:forehome";
  }

  @RequestMapping("forelogout")
  public String logout(HttpSession session, Model model) {
    session.removeAttribute("user");
    return "redirect:forehome";
  }

  @RequestMapping("foreproduct")
  public String product(int pid, Model model) {
    Product product = productService.get(pid);
    List<Review> reviews = reviewService.list(product.getId());
    productService.setImages(product);
    product.setSaleCount(orderItemService.getSaleCount(product.getId()));
    product.setReviewCount(reviews.size());
    List<PropertyValue> propertyValues = propertyValueService.list(product.getId());
    model.addAttribute("p",product);
    model.addAttribute("reviews",reviews);
    model.addAttribute("pvs",propertyValues);
    return "fore/product";
  }

  @ResponseBody
  @RequestMapping("forecheckLogin")
  public String checkLogin(HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
      return "success";
    }
    return "fail";
  }

  @ResponseBody
  @RequestMapping("foreloginAjax")
  public String loginAjax(User user, HttpSession session) {
    user.setName(HtmlUtils.htmlEscape(user.getName()));
    user = userService.get(user.getName(), user.getPassword());
    if (null == user) {
      return "fail";
    }
    session.setAttribute("user", user);
    return "success";
  }
}
