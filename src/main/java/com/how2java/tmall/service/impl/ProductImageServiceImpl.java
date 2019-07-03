package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.ProductImage;
import com.how2java.tmall.bean.ProductImageExample;
import com.how2java.tmall.dao.ProductImageMapper;
import com.how2java.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 10:52
 **/
@Service
public class ProductImageServiceImpl implements ProductImageService {
  @Autowired
  private ProductImageMapper productImageMapper;

  @Override
  public void delete(ProductImage productImage) {
    productImageMapper.deleteByPrimaryKey(productImage.getId());
  }

  @Override
  public List<ProductImage> listSingle(int pid) {
    ProductImageExample example = new ProductImageExample();
    example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type_single);
    return productImageMapper.selectByExample(example);
  }

  @Override
  public List<ProductImage> listDetail(int pid) {
    ProductImageExample example = new ProductImageExample();
    example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type_detail);
    return productImageMapper.selectByExample(example);
  }

  @Override
  public void add(ProductImage productImage) {
    productImageMapper.insert(productImage);
  }

  @Override
  public ProductImage get(int id) {
    return productImageMapper.selectByPrimaryKey(id);
  }
}
