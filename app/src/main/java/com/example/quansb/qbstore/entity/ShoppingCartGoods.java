package com.example.quansb.qbstore.entity;

public class ShoppingCartGoods {
    private int goodsImage;
    private String goodsDetails;
    private String goodsPrices;
    private String goodsColor;
    private String goodsSize;


    public ShoppingCartGoods(int goodsImage,String goodsDetails,String goodsPrices,String goodsColor,String goodsSize) {
       this.goodsImage=goodsImage;
       this.goodsDetails=goodsDetails;
       this.goodsColor=goodsColor;
       this.goodsPrices=goodsPrices;
       this.goodsSize=goodsSize;
    }

    public int getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(int goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public String getGoodsPrices() {
        return goodsPrices;
    }

    public void setGoodsPrices(String goodsPrices) {
        this.goodsPrices = goodsPrices;
    }

    public String getGoodsColor() {
        return goodsColor;
    }

    public void setGoodsColor(String goodsColor) {
        this.goodsColor = goodsColor;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }
}
