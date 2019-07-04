package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.*;
import com.how2java.tmall.dao.CategoryMapper;
import com.how2java.tmall.dao.ProductMapper;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    setCategoryAndFirstProductImage(ps);
    return ps;
  }

  @Override
  public Product get(int id) {
    Product product = productMapper.selectByPrimaryKey(id);
    setCategoryAndFirstProductImage(product);
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

  @Override
  public void fillCategory(Category category) {
    List<Product> products = list(category.getId());
    List<List<Product>> productsByRow = new ArrayList<>();
    int rowCount = 8;
    for (int i = 0; i < products.size(); i += rowCount) {
      if ((i + rowCount - 1) < products.size()) {
        productsByRow.add(products.subList(i, (i + rowCount - 1)));
      } else {
        productsByRow.add(products.subList(i, products.size() - 1));
      }
    }
    category.setProducts(products);
    category.setProductsByRow(productsByRow);
  }

  @Override
  public void fillCategory(List<Category> categories) {
    for (Category category : categories) {
      fillCategory(category);
    }
  }

  @Override
  public void setImages(Product product) {
    List<ProductImage> psis = productImageService.listSingle(product.getId());
    List<ProductImage> pdis = productImageService.listDetail(product.getId());
    product.setProductSingleImages(psis);
    product.setProductDetailImages(pdis);
  }

  private void setCategoryAndFirstProductImage(Product p) {
    p.setCategory(categoryMapper.selectByPrimaryKey(p.getCid()));
    List<ProductImage> pis = productImageService.listSingle(p.getId());
    if (!pis.isEmpty()) {
      p.setFirstProductImage(pis.get(0));
    }
  }

  private void setCategoryAndFirstProductImage(List<Product> products) {
    for (Product product : products) {
      setCategoryAndFirstProductImage(product);
    }
  }

}
