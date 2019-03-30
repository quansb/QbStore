package com.example.quansb.qbstore.activity.homebanner;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BannerJumpActivity extends BaseActivity {
    @Bind(R.id.wv_view)
    WebView wvView;

    @Override
    protected void initData() {

        wvView.getSettings().setJavaScriptEnabled(true);
        wvView.setWebViewClient(new WebViewClient());
        ArrayList arrayList = getIntent().getCharSequenceArrayListExtra("banner_jump_url");
        int position = getIntent().getIntExtra("position", -1);
        String url = arrayList.get(position) + "";
        wvView.loadUrl(url);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_jump_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
