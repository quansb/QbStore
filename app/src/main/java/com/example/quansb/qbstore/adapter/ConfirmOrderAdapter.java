package com.example.quansb.qbstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.GoodsEntity;
import com.mysdk.glide.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmOrderAdapter extends BaseAdapter {
    private Context mContext;
    private  ViewHolder holder;
    private ArrayList<GoodsEntity>goodsList=new ArrayList<>();

    public ArrayList<GoodsEntity> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(ArrayList<GoodsEntity> goodsList) {
        this.goodsList = goodsList;
    }

    public ConfirmOrderAdapter(Context context){
        mContext=context;
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sure_order_goods_layout, parent, false);
            holder= new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().loadImageView(mContext,goodsList.get(position).getGoods_url(),holder.imGoodsImage,false);
        holder.tvGoodsPrices.setText(goodsList.get(position).getGoods_price());
        holder.tvCanBack.setText("七天退换");
        holder.tvGoodsColor.setText("颜色:"+goodsList.get(position).getGoods_colors()+",");
        holder.tvGoodsSize.setText("尺码:"+goodsList.get(position).getGoods_size());
        holder.tvGoodsDetails.setText(goodsList.get(position).getGoods_des());
        holder.tvGoodsMoney.setText(goodsList.get(position).getGoods_price());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.im_goods_image)
        ImageView imGoodsImage;
        @Bind(R.id.tv_goods_details)
        TextView tvGoodsDetails;
        @Bind(R.id.tv_goods_prices)
        TextView tvGoodsPrices;
        @Bind(R.id.tv_goods_color)
        TextView tvGoodsColor;
        @Bind(R.id.tv_goods_size)
        TextView tvGoodsSize;
        @Bind(R.id.ll_goods_parameter_layout)
        LinearLayout llGoodsParameterLayout;
        @Bind(R.id.tv_can_back)
        TextView tvCanBack;
        @Bind(R.id.tv_goods_money)
        TextView tvGoodsMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
