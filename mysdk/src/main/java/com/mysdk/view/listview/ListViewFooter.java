package com.mysdk.view.listview;


import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysdk.R;
import com.mysdk.util.BaseViewUtil;



public class ListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;

    public final static int STATE_READY = 1;

    public final static int STATE_LOADING = 2;

    private Context mContext;

    private View mContentView;

    private View mContainer;

    private View mProgressBar;

    private TextView mHintView;

    private int footerHeight;

    public ListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public ListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setState(int state) {
        mHintView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mHintView.setVisibility(View.INVISIBLE);
        if (state == STATE_READY) {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.listview_footer_hint_ready);
        } else if (state == STATE_LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            // mHintView.setVisibility(View.VISIBLE);
            // mHintView.setText(R.string.listview_footer_hint_normal);
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0)
            return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        return ((LayoutParams) mContentView.getLayoutParams()).bottomMargin;
    }

    public void setVisibleHeight(int height) {
        if (height < 0)
            return;
        if(height > 3 * footerHeight){//限制一下最大的高度，免得滑出去了
            return;
        }
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        return mContainer.getLayoutParams().height;
    }

    /**
     * normal status
     */
    public void normal() {
        mHintView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * loading status
     */
    public void loading() {
        mHintView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = 0;
        mContainer.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = BaseViewUtil.dipToPx(mContext,48);
        mContainer.setLayoutParams(lp);
        mContainer.setVisibility(View.VISIBLE);
    }

    private void initView(Context context) {
        mContext = context;
        mContainer = LayoutInflater.from(mContext).inflate(R.layout.listview_footer, null);
        addView(mContainer);
        mContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = mContainer.findViewById(R.id.xlistview_footer_content);
        mProgressBar = mContainer.findViewById(R.id.xlistview_footer_progressbar);
        mHintView = (TextView) mContainer.findViewById(R.id.xlistview_footer_hint_textview);
        footerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,48,getResources().getDisplayMetrics());
    }

    public int getFooterHeight() {
        return footerHeight;
    }
}

