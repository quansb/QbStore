package com.example.quansb.qbstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.ShoppingCartGoods;

import java.util.ArrayList;

public class ShoppingCartGoodsAdapter extends BaseAdapter {
    private ShoppingCartGoods goods;
    private ArrayList<ShoppingCartGoods> shoppingCartGoods;
    private Context context;

    public ShoppingCartGoodsAdapter(Context context, ArrayList<ShoppingCartGoods> goods) {
        this.context = context;
        this.shoppingCartGoods = goods;
    }


    @Override
    public int getCount() {
        return shoppingCartGoods.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        goods = shoppingCartGoods.get(i);
        ViewHolder itemViewHolder = new ViewHolder();
        if (view == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart_layout, viewGroup,false);
            itemViewHolder.goods_image = itemView.findViewById(R.id.im_goods_image);
            itemViewHolder.goods_price = itemView.findViewById(R.id.tv_goods_prices);
            itemViewHolder.goods_des = itemView.findViewById(R.id.tv_goods_details);
            itemViewHolder.goods_size = itemView.findViewById(R.id.tv_goods_size);
            itemViewHolder.goods_color = itemView.findViewById(R.id.tv_goods_color);
            itemView.setTag(itemViewHolder);
        } else {
            itemView = view;
            itemViewHolder = (ViewHolder) itemView.getTag();
        }
        itemViewHolder.goods_image.setImageResource(goods.getGoodsImage());
        itemViewHolder.goods_des.setText(goods.getGoodsDetails());
        itemViewHolder.goods_price.setText(goods.getGoodsPrices());
        itemViewHolder.goods_color.setText(goods.getGoodsColor());
        itemViewHolder.goods_size.setText(goods.getGoodsSize());
        return itemView;
    }


    class ViewHolder {
        TextView goods_color;
        TextView goods_size;
        TextView goods_des;
        TextView goods_price;
        ImageView goods_image;
    }


}
