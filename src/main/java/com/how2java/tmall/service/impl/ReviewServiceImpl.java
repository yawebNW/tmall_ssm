package com.how2java.tmall.service.impl;

import com.how2java.tmall.bean.Review;
import com.how2java.tmall.bean.ReviewExample;
import com.how2java.tmall.dao.ReviewMapper;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/4 14:21
 **/
@Service
public class ReviewServiceImpl implements ReviewService {
  @Autowired
  private ReviewMapper reviewMapper;
  @Autowired
  private UserService userService;
  @Override
  public void add(Review review) {
    reviewMapper.insert(review);
  }

  @Override
  public void update(Review review) {
    reviewMapper.updateByPrimaryKeySelective(review);
  }

  @Override
  public void delete(int id) {
    reviewMapper.deleteByPrimaryKey(id);
  }

  @Override
  public Review get(int id) {
    Review review = reviewMapper.selectByPrimaryKey(id);
    setUser(review);
    return review;
  }

  @Override
  public List<Review> list(int pid) {
    ReviewExample reviewExample = new ReviewExample();
    reviewExample.createCriteria().andPidEqualTo(pid);
    reviewExample.setOrderByClause("id desc");
    List<Review> reviews = reviewMapper.selectByExample(reviewExample);
    setUser(reviews);
    return reviews;
  }

  @Override
  public int getReviewCount(int pid) {
    return list(pid).size();
  }

  private void setUser(Review review) {
    review.setUser(userService.get(review.getUid()));
  }

  private void setUser(List<Review> reviews) {
    for (Review review : reviews) {
      setUser(review);
    }
  }

}
