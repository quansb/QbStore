package com.example.quansb.qbstore.entity;

public class UserInfo {
    private String nick_name;

    public String getNick_name() {
        return nick_name == null ? "" : nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
