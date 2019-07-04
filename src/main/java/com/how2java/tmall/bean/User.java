package com.how2java.tmall.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {
    @Setter private Integer id;
    private String name;
    private String password;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAnonymousName() {
        String anonymous;
        if (name.length() == 1) {
            anonymous = "*";
        } else if (name.length() == 2) {
            anonymous = name.substring(0,1) + "*";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                if (i == 0 || i == name.length() - 1) {
                    sb.append(name.charAt(i));
                } else {
                    sb.append("*");
                } 
            }
            anonymous = sb.toString();
        } 
        return anonymous;
    }
}