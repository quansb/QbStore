package com.example.quansb.qbstore.entity;

import java.util.ArrayList;
import java.util.List;

public class GoodsInfo extends BaseDataEntity  {
    private GoodsEntity goodsEntity;
    private List<String> goods_urls;
    private List <String>goods_detail_urls;

    public List<String> getGoods_detail_urls() {
        return goods_detail_urls;
    }

    public void setGoods_detail_urls(List<String> goods_detail_urls) {
        this.goods_detail_urls = goods_detail_urls;
    }

    public List<String> getGoods_urls() {
        return goods_urls;
    }

    public void setGoods_urls(List<String> goods_urls) {
        this.goods_urls = goods_urls;
    }

    public GoodsEntity getGoodsEntity() {
        return goodsEntity;
    }

    public void setGoodsEntity(GoodsEntity goodsEntity) {
        this.goodsEntity = goodsEntity;
    }


}
