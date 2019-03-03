package com.mysdk.view.listview;

/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mysdk.R;
import com.mysdk.util.BaseViewUtil;



public class ListViewHeader extends LinearLayout {
    private LinearLayout mContainer;

    private ImageView imageView;

    private int mState = STATE_NORMAL;

    public final static int STATE_NORMAL = 0;

    public final static int STATE_READY = 1;

    public final static int STATE_REFRESHING = 2;

    public ListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public ListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listview_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
        imageView = (ImageView) mContainer.findViewById(R.id.iv_img);
    }

    public void setState(int state) {
        if (state == mState) {
            return;
        }

        if (state == STATE_REFRESHING) { // ��ʾ���

        } else { // ��ʾ��ͷͼƬ

        }

        switch (state) {
        case STATE_NORMAL:

            break;
        case STATE_READY:

            break;
        case STATE_REFRESHING:

            break;
        default:
        }
        if(null != imageView.getDrawable()) {
            ((AnimationDrawable)imageView.getDrawable()).start();
        }
        mState = state;
    }

    public void setVisiableHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
        float ratio = Math.max(0,Math.min(1,(float) (height * 1.0 / BaseViewUtil.dipToPx(getContext(),60))));
        ViewCompat.setScaleY(imageView,ratio);
        ViewCompat.setScaleX(imageView,ratio);
        if(height == 0){
            if(null != imageView.getDrawable()) {
                ((AnimationDrawable)imageView.getDrawable()).stop();
            }
            imageView.setVisibility(GONE);
        }else{
            imageView.setVisibility(VISIBLE);
        }
    }

    public int getVisiableHeight() {
        return mContainer.getLayoutParams().height;
    }

}
