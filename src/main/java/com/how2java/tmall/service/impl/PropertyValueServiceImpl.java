package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.*;
import com.how2java.tmall.dao.CategoryMapper;
import com.how2java.tmall.dao.ProductMapper;
import com.how2java.tmall.dao.PropertyMapper;
import com.how2java.tmall.dao.PropertyValueMapper;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.PixelDrawPipe;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 14:52
 **/
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
  @Autowired
  private PropertyValueMapper propertyValueMapper;
  @Autowired
  private PropertyService propertyService;
  @Override
  public List<PropertyValue> list(int pid) {
    PropertyValueExample propertyValueExample = new PropertyValueExample();
    propertyValueExample.createCriteria().andPidEqualTo(pid);
    List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);
    for (PropertyValue propertyValue : propertyValues) {
      propertyValue.setProperty(propertyService.get(propertyValue.getPtid()));
    }
    return propertyValues;
  }

  @Override
  public PropertyValue get(int ptid, int pid) {
    PropertyValueExample propertyValueExample = new PropertyValueExample();
    propertyValueExample.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
    List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);
    if (propertyValues.isEmpty()) {
      return null;
    } else {
      return propertyValues.get(0);
    }
  }

  @Override
  public void update(PropertyValue propertyValue) {
    propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
  }

  @Override
  public void init(Product product) {
    List<Property> properties = propertyService.list(product.getCid());
    for (Property property : properties) {
      if (get(property.getId(), product.getId())==null) {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.setPid(product.getId());
        propertyValue.setPtid(property.getId());
        propertyValueMapper.insert(propertyValue);
      }
    }
  }
}
