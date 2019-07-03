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
  public void delete(int id) {
    userMapper.deleteByPrimaryKey(id);
  }

  @Override
  public void add(User user) {
    userMapper.insert(user);
  }

  @Override
  public void update(User user) {
    userMapper.updateByPrimaryKeySelective(user);
  }

  @Override
  public List<User> list() {
    return userMapper.selectByExample(new UserExample());
  }

  @Override
  public User get(int uid) {
    return userMapper.selectByPrimaryKey(uid);
  }

  @Override
  public boolean isExist(String name) {
    UserExample userExample = new UserExample();
    userExample.createCriteria().andNameEqualTo(name);
    return !(userMapper.selectByExample(userExample).isEmpty());
  }

  @Override
  public User get(String name, String password) {
    UserExample userExample = new UserExample();
    userExample.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
    List<User> users = userMapper.selectByExample(userExample);
    if (users.isEmpty()) {
      return null;
    }
    return users.get(0);
  }
}
