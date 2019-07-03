package com.how2java.tmall.service;

import com.how2java.tmall.bean.User;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/3 16:51
 **/

public interface UserService {
  void delete(int id);
  void add(User user);
  void update(User user);
  List<User> list();
  User get(int uid);
  boolean isExist(String name);
  User get(String name, String password);
}
