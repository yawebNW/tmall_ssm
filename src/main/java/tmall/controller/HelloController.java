package tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/1 21:38
 **/
@Controller
@RequestMapping("")
public class HelloController {
  @RequestMapping("hello")
  public ModelAndView hello() {
    return new ModelAndView("hello");
  }
}
