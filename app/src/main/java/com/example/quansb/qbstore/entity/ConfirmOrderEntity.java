package com.example.quansb.qbstore.entity;

import java.util.ArrayList;

public class ConfirmOrderEntity extends BaseDataEntity {
    private AddressEntity addressEntity;
    private ArrayList<GoodsEntity> goodsEntities;
    private String totalPrice;

    public ArrayList<GoodsEntity> getGoodsEntities() {
        return goodsEntities;
    }

    public void setGoodsEntities(ArrayList<GoodsEntity> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
