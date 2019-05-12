package com.example.quansb.qbstore.entity;

import java.util.ArrayList;

public class ConfirmOrderEntity extends BaseDataEntity {
    private AddressEntity addressEntity;
    private ArrayList<GoodsEntity> goodsEntities;
    private String totalPrice;

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public ArrayList<GoodsEntity> getGoodsEntities() {
        if (goodsEntities == null) {
            return new ArrayList<>();
        }
        return goodsEntities;
    }

    public void setGoodsEntities(ArrayList<GoodsEntity> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }

    public String getTotalPrice() {
        return totalPrice == null ? "" : totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
