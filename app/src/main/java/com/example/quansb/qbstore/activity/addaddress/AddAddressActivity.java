package com.example.quansb.qbstore.activity.addaddress;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.entity.BaseDataEntity;
import com.example.quansb.qbstore.entity.Event;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.tv_common_right)
    TextView tvCommonRight;
    @Bind(R.id.v_common_line)
    View vCommonLine;
    @Bind(R.id.common_top_layout)
    RelativeLayout commonTopLayout;
    @Bind(R.id.tv_name)
    EditText tvName;
    @Bind(R.id.tv_phone)
    EditText tvPhone;
    @Bind(R.id.tv_postal_code)
    EditText tvPostalCode;
    @Bind(R.id.tv_address)
    EditText tvAddress;
    @Bind(R.id.ll_info_layout)
    LinearLayout llInfoLayout;
    @Bind(R.id.tv_confirm_change)
    TextView tvConfirmChange;
    private Context context=this;
    private String user_id;

    @Override
    protected void initData() {
        tvConfirmChange.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        tvName.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        tvPostalCode.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvBack.setText("新增收货地址");
        tvCommonCentre.setVisibility(View.GONE);
        PreferencesHelp help=new PreferencesHelp(context);
         user_id=help.getUserID();


    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address_layout;
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
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_confirm_change:
                toSaveAddress();
                break;

        }
    }
    private void toSaveAddress() {
        String name=tvName.getText().toString().trim();
        String phone=tvPhone.getText().toString().trim();
        String code=tvPostalCode.getText().toString().trim();
        String address=tvAddress.getText().toString().trim();
        RequestCenter.toSaveAddress(user_id, name, phone, code, address, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                BaseDataEntity entity= (BaseDataEntity) object;
                if(Integer.valueOf(entity.getStatus())>0){
                    LoggerUtil.showToastShort(context,entity.getMsg());
                    Event event = new Event();
                    event.setUpDateAddress(true);
                    event.setUpDateAddressManagement(true);
                    EventBus.getDefault().post(event);
                }else {
                    LoggerUtil.showToastShort(context,entity.getMsg());
                }
            }
            @Override
            public void onFailure(Object object) {

            }
        }, BaseDataEntity.class);
    }
}
