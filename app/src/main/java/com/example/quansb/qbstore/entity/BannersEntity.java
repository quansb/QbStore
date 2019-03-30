package com.example.quansb.qbstore.entity;

import java.util.ArrayList;

public class BannersEntity extends BaseDataEntity {
   private ArrayList<Banner> banners;

    public ArrayList<Banner> getBanners() {
        if (banners==null){
            banners=new ArrayList<>();
        }
        return banners;
    }

    public void setBanners(ArrayList<Banner> banners) {
        this.banners = banners;
    }

    public static class Banner{
        private String banner_url;
        private String banner_color;
        private String banner_jump_url;

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getBanner_color() {
            return banner_color;
        }

        public void setBanner_color(String banner_color) {
            this.banner_color = banner_color;
        }

        public String getBanner_jump_url() {
            return banner_jump_url;
        }

        public void setBanner_jump_url(String banner_jump_url) {
            this.banner_jump_url = banner_jump_url;
        }
    }
}