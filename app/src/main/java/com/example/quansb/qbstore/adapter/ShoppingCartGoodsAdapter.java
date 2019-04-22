package com.example.quansb.qbstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.GoodsEntity;
import com.mysdk.glide.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingCartGoodsAdapter extends BaseAdapter {
    private ArrayList<GoodsEntity> cartGoodsArrayList;
    private Context context;
    private  ViewHolder itemViewHolder;
    private OnClickListener listener;
    public ShoppingCartGoodsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (cartGoodsArrayList==null){
            return 0;
        }
        return cartGoodsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return cartGoodsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (cartGoodsArrayList==null)
            return null;
        itemViewHolder=null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart_layout, viewGroup, false);
            itemViewHolder=new ViewHolder(view);
            view.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ViewHolder) view.getTag();
        }
        final GoodsEntity  goods = cartGoodsArrayList.get(i);
        ImageLoader.getInstance().loadImageView(context, goods.getGoods_url(), itemViewHolder.imGoodsImage, false);
        itemViewHolder.tvGoodsDetails.setText(goods.getGoods_des());
        itemViewHolder.tvGoodsPrices.setText("￥" + goods.getGoods_price());
        itemViewHolder.tvGoodsColor.setText(goods.getGoods_colors() + ";");
        itemViewHolder.tvGoodsSize.setText(goods.getGoods_size());
        itemViewHolder.tvGoodsStatus.setText(goods.getGoods_status());
        if (goods.getIs_choose().equals("0")){
            itemViewHolder.tvGoodsSelectTop.setBackgroundResource(R.drawable.ic_round1);
            itemViewHolder.tvGoodsSelectMiddle.setBackgroundResource(R.drawable.ic_round1);
        }else {
            itemViewHolder.tvGoodsSelectTop.setBackgroundResource(R.drawable.ic_round_red2);
            itemViewHolder.tvGoodsSelectMiddle.setBackgroundResource(R.drawable.ic_round_red2);
        }
        itemViewHolder.rlGoodsSelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              toChangeStatus(goods,i);
            if (listener!=null){
                listener.onClick(goods);
            }
            }
        });
        return view;
    }

    private void toChangeStatus(GoodsEntity goods, int i) {
        if (goods.getIs_choose().equals("0")){
            goods.setIs_choose("1");
        }else if (goods.getIs_choose().equals("1")){
            goods.setIs_choose("0");
        }
    }

    public void clearData() {
        if (cartGoodsArrayList!=null){
            cartGoodsArrayList.clear();
        }
    }


    public interface OnClickListener {
        void onClick(GoodsEntity goodsEntity);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener=listener;
    }


    public void setData(ArrayList<GoodsEntity> CartGoodsArrayList) {
        this.cartGoodsArrayList = CartGoodsArrayList;
    }


    class ViewHolder {
        @Bind(R.id.tv_goods_select_top)
        TextView tvGoodsSelectTop;
        @Bind(R.id.tv_goods_select_middle)
        TextView tvGoodsSelectMiddle;
        @Bind(R.id.rl_goods_select_layout)
        RelativeLayout rlGoodsSelectLayout;
        @Bind(R.id.im_goods_image)
        ImageView imGoodsImage;
        @Bind(R.id.tv_goods_details)
        TextView tvGoodsDetails;
        @Bind(R.id.tv_goods_color)
        TextView tvGoodsColor;
        @Bind(R.id.tv_goods_size)
        TextView tvGoodsSize;
        @Bind(R.id.ll_goods_parameter_layout)
        LinearLayout llGoodsParameterLayout;
        @Bind(R.id.tv_goods_status)
        TextView tvGoodsStatus;
        @Bind(R.id.tv_goods_prices)
        TextView tvGoodsPrices;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
