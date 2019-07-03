package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.User;
import com.how2java.tmall.bean.UserExample;
import com.how2java.tmall.dao.UserMapper;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 16:51
 **/
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserMapper userMapper;
  @Override
  public List<User> list() {
    return userMapper.selectByExample(new UserExample());
  }
}
