package com.example.quansb.qbstore.entity;


public class GoodsEntity  {
    private String goods_url;
    private String goods_des;
    private String goods_price;
    private String goods_id;


    public GoodsEntity( String goods_url, String goods_des,String goods_price,  String goods_id ) {
        this.goods_url=goods_url;
        this.goods_des=goods_des;
        this.goods_id=goods_id;
        this.goods_price=goods_price;
    }

    public String getGoods_url() {
        return goods_url;
    }

    public void setGoods_url(String goods_url) {
        this.goods_url = goods_url;
    }

    public String getGoods_des() {
        return goods_des;
    }

    public void setGoods_des(String goods_des) {
        this.goods_des = goods_des;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }



}
