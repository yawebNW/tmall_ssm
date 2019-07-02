package com.how2java.tmall.util;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @Description TODO
 * @date 2019/7/2 17:18
 **/

public class OverIsMergeablePlugin extends PluginAdapter {
  @Override
  public boolean validate(List<String> warnings) {
    return true;
  }

  @Override
  public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
    try {
      Field field = sqlMap.getClass().getDeclaredField("isMergeable");
      field.setAccessible(true);
      field.setBoolean(sqlMap, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
}
