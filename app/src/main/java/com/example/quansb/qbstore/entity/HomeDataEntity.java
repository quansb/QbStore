package com.example.quansb.qbstore.entity;

import java.util.ArrayList;

public class HomeDataEntity extends BaseDataEntity {
    private BannersEntity bannersEntity;
    private ArrayList<rushGoodsEntities> rushGoodsEntities;
   private ArrayList<GoodsEntity> goodsEntities;

    public ArrayList<GoodsEntity> getGoodsEntities() {
        return goodsEntities;
    }

    public void setGoodsEntities(ArrayList<GoodsEntity> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }

    public BannersEntity getBannersEntity() {
        return bannersEntity;
    }

    public void setBannersEntity(BannersEntity bannersEntity) {
        this.bannersEntity = bannersEntity;
    }

    public ArrayList<HomeDataEntity.rushGoodsEntities> getRushGoodsEntities() {
        return rushGoodsEntities;
    }

    public void setRushGoodsEntities(ArrayList<HomeDataEntity.rushGoodsEntities> rushGoodsEntities) {
        this.rushGoodsEntities = rushGoodsEntities;
    }

    public  class rushGoodsEntities{
        private String left_url;
        private String right_url;
        private String left_name;
        private String right_name;
        private String goods_id;
        private String goods1_id;
        private String title;

        public String getGoods1_id() {
            return goods1_id;
        }

        public void setGoods1_id(String goods1_id) {
            this.goods1_id = goods1_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLeft_url() {
            return left_url;
        }

        public void setLeft_url(String left_url) {
            this.left_url = left_url;
        }

        public String getRight_url() {
            return right_url;
        }

        public void setRight_url(String right_url) {
            this.right_url = right_url;
        }

        public String getLeft_name() {
            return left_name;
        }

        public void setLeft_name(String left_name) {
            this.left_name = left_name;
        }

        public String getRight_name() {
            return right_name;
        }

        public void setRight_name(String right_name) {
            this.right_name = right_name;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
    }









}
