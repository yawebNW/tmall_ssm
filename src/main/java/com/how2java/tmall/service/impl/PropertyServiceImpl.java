package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.Property;
import com.how2java.tmall.bean.PropertyExample;
import com.how2java.tmall.dao.PropertyMapper;
import com.how2java.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/2 21:03
 **/
@Service
public class PropertyServiceImpl implements PropertyService {
  @Autowired
  PropertyMapper propertyMapper;

  @Override
  public void add(Property property) {
    propertyMapper.insert(property);
  }

  @Override
  public void update(Property property) {
    propertyMapper.updateByPrimaryKey(property);
  }

  @Override
  public void delete(Property property) {
    propertyMapper.deleteByPrimaryKey(property.getId());
  }

  @Override
  public List<Property> list(int cid) {
    PropertyExample example = new PropertyExample();
    example.createCriteria().andCidEqualTo(cid);
    return propertyMapper.selectByExample(example);
  }

  @Override
  public Property get(int id) {
    return propertyMapper.selectByPrimaryKey(id);
  }
}
