package com.how2java.tmall.controller;

import com.how2java.tmall.bean.Category;
import com.how2java.tmall.bean.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
