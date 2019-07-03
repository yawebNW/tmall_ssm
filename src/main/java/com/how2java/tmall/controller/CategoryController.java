package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.how2java.tmall.bean.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
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
  public String listCategory(Page page,Model model) {
    PageHelper.offsetPage(page.getStart(), page.getCount());
    PageHelper.orderBy("id desc");
    List<Category> cs = service.list();
    int total = (int) new PageInfo<>(cs).getTotal();
    page.setTotal(total);
    model.addAttribute("cs", cs);
    model.addAttribute("page", page);
    return "admin/listCategory";
  }

  @RequestMapping("admin_category_add")
  public String  addCategory(Category category, UploadFile file, HttpSession session) throws IOException {
    service.add(category);
    File folder = new File(session.getServletContext().getRealPath("img/category"));
    ImageUtil.imageFileProcess(folder,file.getImage(),category.getId());
    return "redirect:/admin_category_list";
  }

  @RequestMapping("admin_category_delete")
  public String  deleteCategory(Category category, HttpSession session) {
    service.delete(category);
    File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
    File file = new File(imageFolder,category.getId()+".jpg");
    file.delete();
    return "redirect:/admin_category_list";
  }

  @RequestMapping("admin_category_edit")
  public String  editCategory(Category category,Model model) {
    category = service.get(category.getId());
    model.addAttribute("c", category);
    return "admin/editCategory";
  }

  @RequestMapping("admin_category_update")
  public String  updateCategory(Category category, UploadFile file, HttpSession session) throws IOException {
    service.update(category);
    if (null != file.getImage()&&(!file.getImage().isEmpty())) {
      File folder = new File(session.getServletContext().getRealPath("img/category"));
      ImageUtil.imageFileProcess(folder,file.getImage(),category.getId());
    }
    return "redirect:/admin_category_list";
  }
}
