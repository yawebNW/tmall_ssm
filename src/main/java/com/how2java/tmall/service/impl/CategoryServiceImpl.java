package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.CategoryExample;
import com.how2java.tmall.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.how2java.tmall.bean.Category;
import com.how2java.tmall.service.CategoryService;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/1 20:07
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public int add(Category category) {
    return categoryMapper.insert(category);
  }

  @Override
  public int update(Category category) {
    return categoryMapper.updateByPrimaryKey(category);
  }

  @Override
  public void delete(Category category) {
    categoryMapper.deleteByPrimaryKey(category.getId());
  }

  @Override
  public Category get(int id) {
    return categoryMapper.selectByPrimaryKey(id);
  }

  @Override
  public List<Category> list() {
    return categoryMapper.selectByExample(new CategoryExample());
  }
}
