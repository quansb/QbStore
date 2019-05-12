package com.example.quansb.qbstore.entity;

import java.util.ArrayList;

public class ShoppingCartGoods extends BaseDataEntity {

    private ArrayList<GoodsEntity> goodsEntities;

    public ArrayList<GoodsEntity> getGoodsEntities() {
        if (goodsEntities == null) {
            return new ArrayList<>();
        }
        return goodsEntities;
    }

    public void setGoodsEntities(ArrayList<GoodsEntity> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }
}
