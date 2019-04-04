package com.example.quansb.qbstore.activity.homeshopping;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReadyToSettle extends BaseActivity implements View.OnClickListener, NestedScrollView.OnScrollChangeListener {
    @Bind(R.id.rl_title_layout)
    RelativeLayout rlTitleLayout;
    @Bind(R.id.sv_scroll_view)
    NestedScrollView svScrollView;
    @Bind(R.id.im_goods_image)
    ImageView imGoodsImage;
    @Bind(R.id.im_back)
    ImageView imBack;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    private Context context;

    @Override
    protected void initData() {
        context = this;
        svScrollView.setOnScrollChangeListener(this);
        imBack.setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ready_settle_text_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.im_back:
                finish();
                break;
        }


    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {


        int maxHeight = imGoodsImage.getHeight();
        int starChangeHeigth = rlTitleLayout.getHeight();
        if (i1>=0&&i1 <= starChangeHeigth) {
            rlTitleLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
            rlTitleLayout.getBackground().mutate().setAlpha(0);
            tvTitleName.setVisibility(View.INVISIBLE);
            tvTitleName.setTextColor(Color.argb( 0, 255,11,11));
            imBack.setImageResource(R.drawable.ic_back_white2);
            imBack.getBackground().mutate().setAlpha(255);
        } else if (i1 > starChangeHeigth && i1 < maxHeight) {
            rlTitleLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
            rlTitleLayout.getBackground().mutate().setAlpha((int) (255 * (1.0 * (i1) / maxHeight)));
            tvTitleName.setVisibility(View.VISIBLE);
            tvTitleName.setTextColor(Color.argb((int) (255 * (1.0 * (i1) / maxHeight)), 255,11,11));
            imBack.setImageResource(R.drawable.ic_back_gay);
            imBack.getBackground().mutate().setAlpha((int) ((255 * (1-(1.0 * (i1) / maxHeight)))));
        } else if (i1 > maxHeight) {
            rlTitleLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
            tvTitleName.setVisibility(View.VISIBLE);
            rlTitleLayout.getBackground().mutate().setAlpha(255);
            tvTitleName.setTextColor(Color.argb( 255 , 255,11,11));
            imBack.getBackground().mutate().setAlpha(0);
        }
    }
}
