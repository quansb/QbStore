package com.example.quansb.qbstore.entity;

import java.util.ArrayList;

public class AddressInfo extends BaseDataEntity {
    private ArrayList<AddressEntity>list;

    public ArrayList<AddressEntity> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(ArrayList<AddressEntity> list) {
        this.list = list;
    }
}
