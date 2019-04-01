package com.example.quansb.qbstore.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.HomeDataEntity;

import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.mysdk.glide.ImageLoader;

import java.util.ArrayList;

import cn.iwgang.countdownview.CountdownView;

public class RushToBuyController implements View.OnClickListener {

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
        viewHolder.imLeftImage.setOnClickListener(this);
        setData(viewHolder, index);

    }

    private void setData(ViewHolder viewHolder, int index) {
        viewHolder.tvBottomLeft.setText(list.get(index).getLeft_name());
        viewHolder.tvBottomRight.setText(list.get(index).getRight_name());
        viewHolder.tvTitle.setText(list.get(index).getTitle());

        if (list.get(index).getTitle().equals("限时抢购")) {
            viewHolder.countdownView.start(100000);
        } else {
            viewHolder.countdownView.setVisibility(View.INVISIBLE);
        }

        ImageLoader.getInstance().loadImageView(mContext, list.get(index).getLeft_url(), viewHolder.imLeftImage, true);
        ImageLoader.getInstance().loadImageView(mContext, list.get(index).getRight_url(), viewHolder.imRightImage, true);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.im_left_image:
                JumpActivityUtil.goToReadyToSettle(mContext);
                break;
            case R.id.im_right_image:
                break;

        }


    }

    private class ViewHolder {
        public TextView tvTopLeft;
        public TextView tvBottomLeft;
        public TextView tvBottomRight;
        public ImageView imLeftImage;
        public ImageView imRightImage;
        public TextView tvTitle;
        public CountdownView countdownView;
    }

    public void initData(ArrayList<HomeDataEntity.rushGoodsEntities> data) {
        list = data;

    }
}
