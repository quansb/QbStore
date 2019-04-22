package com.example.quansb.qbstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.GoodsInfo;
import com.mysdk.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsDetailAdapter extends BaseAdapter {
    private Context context;
    private ViewHolder holder;
    private List<String> goodsDesList;


    public List<String> getGoodsDesList() {
        return goodsDesList;
    }

    public void setGoodsDesList(List<String> goodsDesList) {
        this.goodsDesList = goodsDesList;
    }

    public GoodsDetailAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return goodsDesList.size();
    }

    @Override
    public Object getItem(int i) {
        return goodsDesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_goods_des_layout, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder= (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().loadImageView(context, goodsDesList.get(i), holder.imGoodsImage);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.im_goods_image)
        ImageView imGoodsImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
