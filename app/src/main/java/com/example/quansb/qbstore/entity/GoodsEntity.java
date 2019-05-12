package com.example.quansb.qbstore.entity;



public class GoodsEntity  {
    private String is_choose="0";//0代表没有被选中 1代表被选中
    private String goods_url;
    private String goods_des;
    private String goods_price;
    private String goods_id;
    private String goods_colors;
    private String goods_size;
    private String goods_status;
    private String  courier;
    private String pin;
    private String local;

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getIs_choose() {
        if (is_choose==null){
            is_choose="0";
        }
        return is_choose;
    }

    public void setIs_choose(String is_choose) {
        this.is_choose = is_choose;
    }

    public String getGoods_colors() {
        return goods_colors == null ? "" : goods_colors;
    }

    public void setGoods_colors(String goods_colors) {
        this.goods_colors = goods_colors;
    }

    public String getGoods_size() {
        return goods_size == null ? "" : goods_size;
    }

    public void setGoods_size(String goods_size) {
        this.goods_size = goods_size;
    }

    public String getGoods_status() {
        return goods_status == null ? "" : goods_status;
    }

    public void setGoods_status(String goods_status) {
        this.goods_status = goods_status;
    }

    public GoodsEntity(String goods_url, String goods_des, String goods_price, String goods_id, String goods_colors, String goods_size ) {
        this.goods_url=goods_url;
        this.goods_des=goods_des;
        this.goods_id=goods_id;
        this.goods_price=goods_price;
        this.goods_colors=goods_colors;
        this.goods_size=goods_size;

    }

    public String getGoods_url() {
        return goods_url;
    }

    public void setGoods_url(String goods_url) {
        this.goods_url = goods_url;
    }

    public String getGoods_des() {
        return goods_des == null ? "" : goods_des;
    }

    public void setGoods_des(String goods_des) {
        this.goods_des = goods_des;
    }

    public String getGoods_price() {
        return goods_price == null ? "" : goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_id() {
        return goods_id == null ? "" : goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
}
