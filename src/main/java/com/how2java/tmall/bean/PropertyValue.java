package com.how2java.tmall.bean;

import lombok.Getter;
import lombok.Setter;

public class PropertyValue {
  @Getter @Setter private Integer id;
  @Getter @Setter private Integer pid;
  @Getter @Setter private Integer ptid;
  @Getter private String value;
  @Getter @Setter private Property property;

  public void setValue(String value) {
    this.value = value == null ? null : value.trim();
  }
}