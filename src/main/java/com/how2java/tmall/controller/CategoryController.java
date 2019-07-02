package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.how2java.tmall.bean.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadFile;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/1 20:28
 **/
@Controller
@RequestMapping("")
public class CategoryController {
  @Autowired
  private CategoryService service;

  @RequestMapping("admin_category_list")
  public ModelAndView listCategory(Page page) {
    PageHelper.offsetPage(page.getStart(), page.getCount());
    List<Category> cs = service.list();
    int total = (int) new PageInfo<>(cs).getTotal();
    page.setTotal(total);
    ModelAndView mav = new ModelAndView("admin/listCategory");
    mav.addObject("cs", cs);
    mav.addObject("page", page);
    return mav;
  }

  @RequestMapping("admin_category_add")
  public ModelAndView addCategory(Category category, UploadFile file, HttpServletRequest request) throws IOException {
    service.add(category);
    fileProcess(category, file, request);
    return new ModelAndView("redirect:admin_category_list");
  }

  @RequestMapping("admin_category_delete")
  public ModelAndView deleteCategory(Category category) {
    service.delete(category);
    return new ModelAndView("redirect:admin_category_list");
  }

  @RequestMapping("admin_category_edit")
  public ModelAndView editCategory(Category category) {
    category = service.get(category.getId());
    ModelAndView mav = new ModelAndView("admin/editCategory");
    mav.addObject("c", category);
    return mav;
  }

  @RequestMapping("admin_category_update")
  public ModelAndView updateCategory(Category category, UploadFile file, HttpServletRequest request) throws IOException {
    service.update(category);
    fileProcess(category, file, request);
    return new ModelAndView("redirect:admin_category_list");
  }

  private void fileProcess(Category category, UploadFile file, HttpServletRequest request) throws IOException {
    String fileName = category.getId()+".jpg";
    File newFile = new File(request.getServletContext().getRealPath("img/category"),fileName);
    file.getFilepath().transferTo(newFile);
  }
}
