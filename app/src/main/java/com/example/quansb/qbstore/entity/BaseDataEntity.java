package com.example.quansb.qbstore.entity;

import java.io.Serializable;

public class BaseDataEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status="-1";
    private String msg;
    private Object data;

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
