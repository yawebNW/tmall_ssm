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
import java.text.SimpleDateFormat;
import java.util.*;

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
  @Autowired
  private OrderService orderService;

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

  @RequestMapping("forecategory")
  public String category(int cid, String sort, Model model) {
    Category category = categoryService.get(cid);
    productService.fillCategory(category);
    List<Product> products = category.getProducts();
    for (Product product : products) {
      product.setSaleCount(orderItemService.getSaleCount(product.getId()));
      product.setReviewCount(reviewService.getReviewCount(product.getId()));
    }
    if (null != sort) {
      switch (sort) {
        case "review":
          products.sort(Comparator.comparingInt(Product::getReviewCount).reversed());
          break;
        case "date":
          products.sort(Comparator.comparing(Product::getCreateDate).reversed());
          break;
        case "saleCount":
          products.sort(Comparator.comparing(Product::getSaleCount).reversed());
          break;
        case "price":
          products.sort(Comparator.comparing(Product::getPromotePrice));
          break;
        case "all":
          products.sort((p1,p2)->(p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p1.getSaleCount()));
          break;
        default:
          break;
      }
    }
    category.setProducts(products);
    model.addAttribute("c",category);
    return "fore/category";
  }

  @RequestMapping("foresearch")
  public String search(String keyword, Model model) {
    keyword = keyword.trim();
    List<Product> products = productService.search(keyword);
    model.addAttribute("ps",products);
    return "fore/searchResult";
  }

  @RequestMapping("forebuyone")
  public String buyOne(int num, int pid, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    OrderItem orderItem = orderItemService.getByProductAndUser(pid, user.getId());
    if (orderItem == null) {
      orderItem = new OrderItem();
      orderItem.setNumber(num);
      orderItem.setPid(pid);
      orderItem.setUid(user.getId());
      orderItemService.add(orderItem);
    } else {
      orderItem.setNumber(orderItem.getNumber()+num);
      orderItemService.update(orderItem);
    }
    return "redirect:forebuy?oiid="+orderItem.getId();
  }

  @RequestMapping("forebuy")
  public String buy(String[] oiid, Model model, HttpSession session) {
    List<OrderItem> orderItems = new ArrayList<>();
    float total = 0;
    for (String oi : oiid) {
      OrderItem  orderItem = orderItemService.get(Integer.parseInt(oi));
      total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
      orderItems.add(orderItem);
    }
    session.setAttribute("ois",orderItems);
    model.addAttribute("total",total);
    return "fore/buy";
  }

  @ResponseBody
  @RequestMapping("foreaddCart")
  public String addCart(int pid, int num, HttpSession session) {
    User user = (User)session.getAttribute("user");
    OrderItem orderItem = orderItemService.getByProductAndUser(pid, user.getId());
    if (orderItem == null) {
      orderItem = new OrderItem();
      orderItem.setNumber(num);
      orderItem.setPid(pid);
      orderItem.setUid(user.getId());
      orderItemService.add(orderItem);
    } else {
      orderItem.setNumber(orderItem.getNumber()+num);
      orderItemService.update(orderItem);
    }
    return "success";
  }

  @RequestMapping("forecart")
  public String cart(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
    model.addAttribute("ois",orderItems);
    return "fore/cart";
  }

  @ResponseBody
  @RequestMapping("foredeleteOrderItem")
  public String deleteOrderItem(int oiid) {
    orderItemService.delete(oiid);
    return "success";
  }

  @ResponseBody
  @RequestMapping("forechangeOrderItem")
  public String changeOrderItem(int pid, int number, HttpSession session) {
    User user = (User) session.getAttribute("user");
    OrderItem orderItem = orderItemService.getByProductAndUser(pid,user.getId() );
    orderItem.setNumber(number);
    orderItemService.update(orderItem);
    return "success";
  }

  @RequestMapping("forecreateOrder")
  public String createOrder(Order order, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    order.setCreateDate(new Date());
    String orderCode = new SimpleDateFormat("yyyymmddHHMMss").format(order.getCreateDate())+(new Random().nextInt(9000)+1000);
    order.setUid(user.getId());
    order.setStatus(OrderService.waitPay);
    order.setOrderCode(orderCode);
    List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");
    float total = orderService.add(order,orderItems);
    return "redirect:forealipay?oid="+order.getId()+"&total="+total;
  }

  @RequestMapping("forepayed")
  public String payed(int oid, float total, Model model) {
    Order order = orderService.get(oid);
    order.setStatus(OrderService.waitDelivery);
    order.setPayDate(new Date());
    orderService.update(order);
    model.addAttribute("o",order);
    return "fore/payed";
  }

  @RequestMapping("forebought")
  public String forebought(HttpSession session, Model model) {
    User user = (User) session.getAttribute("user");
    List<Order> orders = orderService.listByUser(user.getId(),OrderService.delete);
    orderItemService.fillOrder(orders);
    model.addAttribute("os",orders);
    return "fore/bought";
  }

  @ResponseBody
  @RequestMapping("foredeleteOrder")
  public String deleteOrder(int oid) {
    Order order = orderService.get(oid);
    order.setStatus(OrderService.delete);
    orderService.update(order);
    return "success";
  }

  @RequestMapping("foreconfirmPay")
  public String confirmPay(int oid, Model model) {
    Order order = orderService.get(oid);
    orderItemService.fillOrder(order);
    model.addAttribute("o",order);
    return "fore/confirmPay";
  }

  @RequestMapping("foreorderConfirmed")
  public String orderConfirmed(int oid) {
    Order order = orderService.get(oid);
    order.setStatus(OrderService.waitReview);
    order.setConfirmDate(new Date());
    orderService.update(order);
    return "fore/orderConfirmed";
  }

  @RequestMapping("forereview")
  public String review(int oid, Model model) {
    Order order = orderService.get(oid);
    List<OrderItem> orderItems = orderItemService.list(oid);
    orderItemService.fillOrder(order);
    Product product = orderItems.get(0).getProduct();
    productService.setSaleAndReviewCount(product);
    List<Review> reviews = reviewService.list(product.getId());
    model.addAttribute("o",order);
    model.addAttribute("p",product);
    model.addAttribute("reviews",reviews);
    return "fore/review";
  }

  @RequestMapping("foredoreview")
  public String doreview(int pid, int oid, String content, Model model, HttpSession session) {
    Order order = orderService.get(oid);
    order.setStatus(OrderService.finish);
    orderService.update(order);
    User user = (User) session.getAttribute("user");
    Review review = new Review();
    review.setPid(pid);
    review.setContent(HtmlUtils.htmlEscape(content));
    review.setUid(user.getId());
    review.setCreateDate(new Date());
    reviewService.add(review);
    return "redirect:forereview?oid="+oid+"&showonly="+true;
  }
}
