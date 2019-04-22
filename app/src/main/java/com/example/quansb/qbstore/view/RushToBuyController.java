package com.example.quansb.qbstore.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.GoodsInfo;
import com.example.quansb.qbstore.entity.HomeDataEntity;

import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.glide.ImageLoader;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;

import java.util.ArrayList;

import cn.iwgang.countdownview.CountdownView;

public class RushToBuyController   {

    private Context mContext;
    private TextView tvTopLeft;
    private TextView tvBottomLeft;
    private TextView tvBottomRight;
    private TextView tvTitle;
    private ImageView imLeftImage;
    private ImageView imRightImage;
    private CountdownView countdownView;


    private ArrayList<HomeDataEntity.rushGoodsEntities> list;

    public RushToBuyController(Context context) {
        mContext = context;
    }

    public void loadView(int layoutId, ViewGroup parentLayout, int index) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, parentLayout, false);
        parentLayout.addView(view);
        ViewHolder viewHolder = new ViewHolder();
        countdownView = view.findViewById(R.id.cv_count_down);
        tvTopLeft = view.findViewById(R.id.tv_top_left_text);
        tvBottomLeft = view.findViewById(R.id.tv_bottom_left_text);
        tvBottomRight = view.findViewById(R.id.tv_bottom_right_text);
        tvTitle = view.findViewById(R.id.tv_top_left_text);
        imLeftImage = view.findViewById(R.id.im_left_image);
        imRightImage = view.findViewById(R.id.im_right_image);
        viewHolder.tvTopLeft = tvTopLeft;
        viewHolder.tvBottomLeft = tvBottomLeft;
        viewHolder.tvBottomRight = tvBottomRight;
        viewHolder.imLeftImage = imLeftImage;
        viewHolder.imRightImage = imRightImage;
        viewHolder.tvTitle = tvTitle;
        viewHolder.countdownView = countdownView;
        setData(viewHolder, index);

    }

    private void setData(ViewHolder viewHolder, final int index) {
        viewHolder.tvBottomLeft.setText(list.get(index).getLeft_name());
        viewHolder.tvBottomRight.setText(list.get(index).getRight_name());
        viewHolder.tvTitle.setText(list.get(index).getTitle());
        if (list.get(index).getTitle().equals("限时抢购")) {
            viewHolder.countdownView.start(10000000);
        } else {
            viewHolder.countdownView.setVisibility(View.INVISIBLE);
        }
        ImageLoader.getInstance().loadImageView(mContext, list.get(index).getLeft_url(), viewHolder.imLeftImage, true);
        ImageLoader.getInstance().loadImageView(mContext, list.get(index).getRight_url(), viewHolder.imRightImage, true);

        /**
         * 左图请求 有图请求
         */
            viewHolder.imLeftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  toGetGoodsDetail(index );
                }
            });

            viewHolder.imRightImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

    }

    private void toGetGoodsDetail(int i) {
        PreferencesHelp help=new PreferencesHelp(mContext);
        JumpActivityUtil.goToReadyToSettle(mContext,list.get(i).getGoods_id());
    }


    private class ViewHolder {
        TextView tvTopLeft;
        TextView tvBottomLeft;
        TextView tvBottomRight;
        ImageView imLeftImage;
        ImageView imRightImage;
        TextView tvTitle;
        CountdownView countdownView;
    }

    public void initData(ArrayList<HomeDataEntity.rushGoodsEntities> data) {
        list = data;

    }
}
