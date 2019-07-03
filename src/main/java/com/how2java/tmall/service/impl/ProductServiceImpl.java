package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.*;
import com.how2java.tmall.dao.CategoryMapper;
import com.how2java.tmall.dao.ProductImageMapper;
import com.how2java.tmall.dao.ProductMapper;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/2 22:07
 **/
@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductMapper productMapper;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public List<Product> list(int cid) {
    ProductExample example = new ProductExample();
    example.createCriteria().andCidEqualTo(cid);
    List<Product> ps = productMapper.selectByExample(example);
    /*设置Category及firstProductImage属性*/
    for (Product p : ps) {
      p.setCategory(categoryMapper.selectByPrimaryKey(p.getCid()));
      List<ProductImage> pis = productImageService.listSingle(p.getId());
      if (!pis.isEmpty()) {
        p.setFirstProductImage(pis.get(0));
      }
    }
    return ps;
  }

  @Override
  public Product get(int id) {
    Product product = productMapper.selectByPrimaryKey(id);
    product.setCategory(categoryMapper.selectByPrimaryKey(product.getCid()));
    return product;
  }

  @Override
  public void add(Product product) {
    product.setCreateDate(new Date());
    productMapper.insert(product);
  }

  @Override
  public void update(Product product) {
    productMapper.updateByPrimaryKey(product);
  }

  @Override
  public void delete(int id) {
    productMapper.deleteByPrimaryKey(id);
  }
}
