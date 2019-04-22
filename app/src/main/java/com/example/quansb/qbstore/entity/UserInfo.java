package com.example.quansb.qbstore.entity;

import java.io.Serializable;

public class UserInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String status;
    private String msg;
    private String user_id;
    private String user_name;
    private String age;
    private String avatar_img;
    private String sex;
    private String money;
    private String has_pwd;

    public String getHas_pwd() {
        return has_pwd;
    }

    public void setHas_pwd(String has_pwd) {
        this.has_pwd = has_pwd;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAvatar_img() {
        return avatar_img;
    }

    public void setAvatar_img(String avatar_img) {
        this.avatar_img = avatar_img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
