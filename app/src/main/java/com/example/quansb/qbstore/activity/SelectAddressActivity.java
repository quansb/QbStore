package com.example.quansb.qbstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.adapter.SelectAddressAdapter;
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

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectAddressActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.tv_common_right)
    TextView tvCommonRight;
    @Bind(R.id.lv_list_view)
    ListView lvListView;
    private Context context=this;
    private AddressInfo addressInfo;
    private SelectAddressAdapter selectAddressAdapter =new SelectAddressAdapter(context);
    private EventBus eventBus=new EventBus();
    @Override
    protected void initData() {

        tvCommonCentre.setVisibility(View.GONE);
        tvBack.setOnClickListener(this);
        tvCommonRight.setVisibility(View.VISIBLE);
        tvCommonRight.setOnClickListener(this);
        tvCommonRight.setText("管理");
        tvBack.setText("选择收货地址");
        toGetData();
    }

    @Override
    protected void initView() {

    }

    private void toGetData() {
        PreferencesHelp help = new PreferencesHelp(context);
        final String id = help.getUserID();
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

        selectAddressAdapter.setOnOnClickListener(new SelectAddressAdapter.OnClickListener() {
            @Override
            public void onClick(AddressEntity address,int position) {
                Intent intent=new Intent();
                intent.putExtra("name",address.getConsignee());
                intent.putExtra("phone",address.getPhone());
                intent.putExtra("address",address.getAddress());
                setResult(1,intent);
                selectAddressAdapter.notifyDataSetChanged();
                finish();
            }
        });
    }





    private void toGetAddress(){
        ArrayList<AddressEntity> arrayList = addressInfo.getList();
        selectAddressAdapter.setData(arrayList);
        lvListView.setAdapter(selectAddressAdapter);
        selectAddressAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_management_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
      EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(Event event) {
        if(event.isUpDateAddress()){
            toGetData();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_common_right:
                JumpActivityUtil.goToAddressManagementActivity(context);
                break;
        }
    }
}
