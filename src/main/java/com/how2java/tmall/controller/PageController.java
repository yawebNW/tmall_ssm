package com.how2java.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 23:11
 **/
@Controller
@RequestMapping("")
public class PageController {
  @RequestMapping("registerPage")
  public String registerPage() {
    return "fore/register";
  }
  @RequestMapping("registerSuccessPage")
  public String registerSuccessPage() {
    return "fore/registerSuccess";
  }
  @RequestMapping("loginPage")
  public String loginPage() {
    return "fore/login";
  }
  @RequestMapping("forealipay")
  public String alipay(){
    return "fore/alipay";
  }
}
