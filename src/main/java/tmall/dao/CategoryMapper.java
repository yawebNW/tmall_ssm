package tmall.dao;

import tmall.bean.Category;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/1 19:56
 **/

public interface CategoryMapper {
  int add(Category category);
  int update(Category category);
  void delete(Category category);
  Category get(int id);
  List<Category> list();
}
