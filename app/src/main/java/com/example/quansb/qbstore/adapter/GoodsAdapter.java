package com.example.quansb.qbstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.GoodsEntity;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.glide.ImageLoader;
import com.mysdk.logger.LoggerUtil;

import java.util.ArrayList;

public class GoodsAdapter extends RecyclerView.Adapter {

    private ArrayList<GoodsEntity> goodsEntityArrayList;
    private Context mcontent;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView goodsImage;
        TextView goodsPrices;
        TextView goodsDetails;
        LinearLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImage=itemView.findViewById(R.id.im_goods_image);
            goodsPrices=itemView.findViewById(R.id.tv_goods_prices);
            goodsDetails=itemView.findViewById(R.id.tv_goods_details);
            itemLayout=itemView.findViewById(R.id.ll_item_layout);
        }
    }

    public GoodsAdapter(ArrayList<GoodsEntity> goods, Context context){
        this.goodsEntityArrayList =goods;
        mcontent=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goods_layout,viewGroup,false);
       ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder1= (ViewHolder) viewHolder;
         final GoodsEntity goodsEntity = goodsEntityArrayList.get(i);
        ImageLoader.getInstance().loadImageView(mcontent,goodsEntity.getGoods_url(),viewHolder1.goodsImage,false);
        viewHolder1.goodsDetails.setText(goodsEntity.getGoods_des());
        viewHolder1.goodsPrices.setText(goodsEntity.getGoods_price());

        viewHolder1.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesHelp help=new PreferencesHelp(mcontent);
                help.getUserID();
                if(help.getUserID()==null||"".equals(help.getUserID())){
                     JumpActivityUtil.goToLoginActivity(mcontent);
                    LoggerUtil.showToastShort(mcontent,"登录帐号可以获取更多的信息哦");
                }else {
                    JumpActivityUtil.goToReadyToSettle(mcontent,goodsEntity.getGoods_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsEntityArrayList.size();
    }

}
