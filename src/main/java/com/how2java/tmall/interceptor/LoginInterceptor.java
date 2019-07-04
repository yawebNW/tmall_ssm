package com.how2java.tmall.interceptor;

import com.how2java.tmall.bean.Category;
import com.how2java.tmall.bean.OrderItem;
import com.how2java.tmall.bean.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/4 19:46
 **/

public class LoginInterceptor extends HandlerInterceptorAdapter {
  private String[] noNeedAuth = {"home", "register", "login", "checkLogin", "loginAjax", "product", "category", "search"};

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    String contextPath = session.getServletContext().getContextPath();
    String uri = request.getRequestURI();
    uri = StringUtils.remove(uri, contextPath);
    String method = StringUtils.substringAfterLast(uri, "/fore");
    for (String s : noNeedAuth) {
      if (method.equals(s)) {
        return true;
      }
    }
    User user = (User) session.getAttribute("user");
    if (null == user) {
      response.sendRedirect("loginPage");
      return false;
    }
    return true;
  }
}
