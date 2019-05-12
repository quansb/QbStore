package com.example.quansb.qbstore.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.base.WrHandler;
import com.example.quansb.qbstore.entity.BaseDataEntity;
import com.example.quansb.qbstore.entity.GoodsInfo;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassWordDialog extends BottomDialog {
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
    @Bind(R.id.my_pwd_view)
    MyPassWordView myPwdView;
    @Bind(R.id.tv_1)
    TextView tv1;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.tv_3)
    TextView tv3;
    @Bind(R.id.tv_4)
    TextView tv4;
    @Bind(R.id.tv_5)
    TextView tv5;
    @Bind(R.id.tv_6)
    TextView tv6;
    @Bind(R.id.tv_7)
    TextView tv7;
    @Bind(R.id.tv_8)
    TextView tv8;
    @Bind(R.id.tv_9)
    TextView tv9;
    @Bind(R.id.tv_0)
    TextView tv0;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    private String passWord = "";
    private int passWordSum = 6;
    private int add = 0;
    private Context mContext;
    private StringBuffer buffer = new StringBuffer();
    private boolean flag = false;   //true 第二次确认密码  false 第一次确认密码
    private String firstWord;
    private String lastWord;
    private String allPrice;
    private String goodsID;
    private Activity activity;
    private WrHandler wrHandler;
    private Handler.Callback callback;
    private MyHandler myHandler;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    PreferencesHelp help = new PreferencesHelp(mContext);

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_payment_settings_layout, container, false);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initData();
        // toGetPassWord();
        return rootView;
    }

    private void initData() {

        tvBack.setText("");
        assert getTag() != null;
        if (getTag().equals("has_pwd")) {
            tvCommonCentre.setText("请输入支付密码");
        } else if (getTag().equals("set_pwd")) {
            tvCommonCentre.setText("设置密码");
        }
        myPwdView.setCircleColor(getResources().getColor(R.color.color_3e3e3e));
        myPwdView.setLineColor(getResources().getColor(R.color.color_cbcbcb));
        myPwdView.setPassWordNum(passWordSum);
        myPwdView.setContext(mContext);
        myHandler = new MyHandler(activity);
    }


    private void toGetPassWord() {
        callback = new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0x11) {
                    reSet();
                }
                return true;
            }
        };
        wrHandler = new WrHandler(callback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_back, R.id.tv_common_centre, R.id.tv_common_right, R.id.v_common_line, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9, R.id.tv_0, R.id.tv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                dismiss();
                break;
            case R.id.tv_1:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(1);
                break;
            case R.id.tv_2:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(2);
                break;
            case R.id.tv_3:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(3);
                break;
            case R.id.tv_4:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(4);
                break;
            case R.id.tv_5:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(5);
                break;
            case R.id.tv_6:
                add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(6);
                break;
            case R.id.tv_7:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(7);
                break;
            case R.id.tv_8:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(8);
                break;
            case R.id.tv_9:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(9);
                break;
            case R.id.tv_0:
                if (add < passWordSum)
                    add++;
                myPwdView.addCircle(add);
                buffer = buffer.append(0);
                break;
            case R.id.tv_delete:
                if (add > 0) {
                    myPwdView.addCircle(--add);
                }
                if (buffer.length() > 0) {
                    buffer.delete(buffer.length() - 1, buffer.length());
                }
                break;
        }


        //消息处理 handler
        if (buffer.length() == 6) {
            myHandler.sendEmptyMessageDelayed(0x11, 500);
        }
    }


    private void reSet() {
        if (buffer.length() == 6) {
            passWord = buffer.toString();
            assert getTag() != null;
            if (getTag().equals("has_pwd")) {     //有支付密码 就支付
                payGoodsMoney();
            } else if (getTag().equals("set_pwd")) {     //没有支付密码 设置支付密码
                if (!flag) {                                 //第一次输入密码
                    firstWord = passWord;
                    tvCommonCentre.setText("再次确认密码");
                    myPwdView.addCircle(0);
                    add = 0;
                    buffer.delete(0, buffer.length());
                    flag = true;
                } else {                                       //第二次输入密码
                    lastWord = passWord;
                    if (lastWord.equals(firstWord)) {        //两次输入密码一致    发送网络请求
                        setPayPassWord();
                        flag = false;
                    } else {                         //如果两次输入密码不一致
                        Toast.makeText(mContext, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }
        }
    }

    private void setPayPassWord() {
        RequestCenter.toSetPayPassWord(help.getUserID(), passWord, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                BaseDataEntity baseDataEntity = (BaseDataEntity) object;
                if (Integer.valueOf(baseDataEntity.getStatus()) > 0) {
                    LoggerUtil.showToastShort(mContext, baseDataEntity.getMsg());
                    PreferencesHelp help=new PreferencesHelp(mContext);
                    help.putString("has_pwd","1");
                    dismiss();
                } else {
                    LoggerUtil.showToastShort(mContext, baseDataEntity.getMsg());
                }
            }

            @Override
            public void onFailure(Object object) {

            }
        }, BaseDataEntity.class);
    }

    private void payGoodsMoney() {
        RequestCenter.toPayGoodsMoney(help.getUserID(), goodsID, allPrice, passWord, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                BaseDataEntity baseDataEntity = (BaseDataEntity) object;
                if (Integer.valueOf(baseDataEntity.getStatus()) > 0) {
                    LoggerUtil.showToastShort(mContext, baseDataEntity.getMsg());
                    dismiss();
                } else {
                    LoggerUtil.showToastShort(mContext, baseDataEntity.getMsg());
                    myPwdView.addCircle(0);
                    add = 0;
                    buffer.delete(0, buffer.length());
                }
            }

            @Override
            public void onFailure(Object object) {
            }
        }, BaseDataEntity.class);
    }

    class MyHandler extends Handler {
        WeakReference<Activity> weakReference;
        MyHandler(Activity activity) {
            weakReference = new WeakReference<Activity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference.get() != null) {
                if (msg.what == 0x11) {
                    reSet();
                }
            }
        }
    }


}
