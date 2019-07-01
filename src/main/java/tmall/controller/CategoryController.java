package tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tmall.bean.Category;
import tmall.service.CategoryService;
import tmall.util.Page;

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

  @RequestMapping("listCategory")
  public ModelAndView listCategory(Page page) {
    System.out.println(page.getStart());
    PageHelper.offsetPage(page.getStart(), page.getCount());
    List<Category> cs = service.list();
    int total = (int) new PageInfo<>(cs).getTotal();
    page.setTotal(total);
    ModelAndView mav = new ModelAndView("admin/listCategory");
    mav.addObject("cs", cs);
    mav.addObject("page", page);
    return mav;
  }
}
