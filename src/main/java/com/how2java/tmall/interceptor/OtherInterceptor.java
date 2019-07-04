package com.how2java.tmall.interceptor;

import com.how2java.tmall.bean.Category;
import com.how2java.tmall.bean.OrderItem;
import com.how2java.tmall.bean.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
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
 * @date 2019/7/4 20:20
 **/

public class OtherInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private OrderItemService orderItemService;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    HttpSession session = request.getSession();
    String contextPath = request.getServletContext().getContextPath();
    session.setAttribute("contextPath", contextPath);
    List<Category> categories = categoryService.list();
    session.setAttribute("cs", categories);
    User user = (User) request.getSession().getAttribute("user");
    if (null != user) {
      List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
      session.setAttribute("cartTotalItemNumber", orderItems.size());
    }
  }
}
