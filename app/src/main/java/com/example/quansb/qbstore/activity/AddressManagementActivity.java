package com.example.quansb.qbstore.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.adapter.AddressAdapter;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.entity.AddressEntity;
import com.example.quansb.qbstore.entity.AddressInfo;
import com.example.quansb.qbstore.entity.Event;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.Logger;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressManagementActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.lv_list_view)
    ListView lvListView;
    @Bind(R.id.ll_add_address)
    LinearLayout llAddAddress;
    private Context context = this;
    private AddressInfo addressInfo;
    private AddressAdapter adapter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        llAddAddress.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        tvBack.setText("管理收货地址");
        tvCommonCentre.setVisibility(View.GONE);
        adapter = new AddressAdapter(context);
        toGetData();
    }

    /**
     * 发送网络请求 获取地址数据
     */
    private void toGetData() {
        PreferencesHelp help = new PreferencesHelp(context);
        String id = help.getUserID();
        RequestCenter.toGetAddressData(id, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                addressInfo = (AddressInfo) object;
                if (Integer.valueOf(addressInfo.getStatus()) > 0) {
                    toGetAddress();
                } else {
                    LoggerUtil.showToastShort(context, addressInfo.getMsg());
                }
            }

            @Override
            public void onFailure(Object object) {
                Logger.showToastShort(getString(R.string.net_exception));
            }
        }, AddressInfo.class);

        adapter.setOnOnClickListener(new AddressAdapter.OnClickListener() {
            @Override
            public void onClick(AddressEntity address) {
                adapter.notifyDataSetChanged();
                Event event = new Event();
                event.setUpDateAddress(true);
                EventBus.getDefault().post(event);
            }
        });
    }

    private void toGetAddress() {
        ArrayList<AddressEntity> arrayList = addressInfo.getList();
        adapter.setData(arrayList);
        lvListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_management_layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_add_address:
                JumpActivityUtil.goToAddAddressActivity(context);
                break;
        }
    }

    @Subscribe
    public void onEvent(Event event) {
        if (event.isUpDateAddressManagement()) {
            toGetData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
