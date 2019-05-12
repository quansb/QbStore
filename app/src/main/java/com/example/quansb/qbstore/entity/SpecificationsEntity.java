package com.example.quansb.qbstore.entity;

import java.util.ArrayList;

public class SpecificationsEntity extends BaseDataEntity {
    private ArrayList<String>sizes;
    private ArrayList<String>colors;

    public ArrayList<String> getSizes() {
        if (sizes == null) {
            return new ArrayList<>();
        }
        return sizes;
    }

    public void setSizes(ArrayList<String> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<String> getColors() {
        if (colors == null) {
            return new ArrayList<>();
        }
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }
}
