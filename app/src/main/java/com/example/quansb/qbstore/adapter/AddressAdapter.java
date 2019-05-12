package com.example.quansb.qbstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.AddressEntity;
import com.example.quansb.qbstore.entity.BaseDataEntity;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.Logger;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder holder;
    private ArrayList<AddressEntity> list;
    private OnClickListener listener;
    private int defaultIndex; //默认的索引


    public AddressAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<AddressEntity> addressInfo) {
        this.list = addressInfo;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_address_layout, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final AddressEntity addressEntity = list.get(i);
        holder.tvName.setText(addressEntity.getConsignee());
        holder.tvTelephone.setText(addressEntity.getPhone());
        holder.tvAddress.setText(addressEntity.getAddress());
        if (addressEntity.getIs_default().equals("0")) {
            holder.tvSelect.setBackgroundResource(R.drawable.ic_round1);
        } else if (addressEntity.getIs_default().equals("1")) {
            defaultIndex=i;
            holder.tvSelect.setBackgroundResource(R.drawable.ic_round_red2);
        }

        holder.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSetDefaultAddress(addressEntity, i);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDeleteAddress(addressEntity,i);
            }
        });



        return view;
    }

    private void toDeleteAddress(final AddressEntity addressEntity, final int i) {
        PreferencesHelp help = new PreferencesHelp(context);
        String user_id = help.getUserID();
        if(addressEntity.getAddress_id()!=null||"".equals(addressEntity.getAddress_id())){
            RequestCenter.toDeleteAddress(user_id, addressEntity.getAddress_id(), new DisposeDataListener() {
                @Override
                public void onSuccess(Object object) {
                    BaseDataEntity baseDataEntity= (BaseDataEntity) object;
                    if (Integer.valueOf(baseDataEntity.getStatus()) > 0) {
                 //       toUpdateUI(addressEntity,i);
                        if (listener != null) {
                            listener.onClick(addressEntity);
                        }
                    } else {
                        LoggerUtil.showToastShort(context, baseDataEntity.getMsg());
                    }
                }

                @Override
                public void onFailure(Object object) {

                }
            },BaseDataEntity.class);
        }

    }


    private void toSetDefaultAddress(final AddressEntity addressEntity, final int index) {

        if (addressEntity.getIs_default().equals("0")) {
            PreferencesHelp help = new PreferencesHelp(context);
            String user_id = help.getUserID();
            RequestCenter.toSetDefaultAddress(user_id, addressEntity.getAddress_id(), new DisposeDataListener() {
                @Override
                public void onSuccess(Object object) {
                    BaseDataEntity entity = (BaseDataEntity) object;
                    if (Integer.valueOf(entity.getStatus()) > 0) {
                        toUpdateUI(addressEntity,index);
                        if (listener != null) {
                            listener.onClick(addressEntity );
                        }
                    } else {
                        LoggerUtil.showToastShort(context, entity.getMsg());
                    }
                }
                @Override
                public void onFailure(Object object) {
                    Logger.showToastShort(context.getString(R.string.net_exception));
                }
            }, BaseDataEntity.class);

        }
    }


    /**
     *
     * @param addressEntity
     * @param i   当前被选中的第index个  item
     */
    private void toUpdateUI(AddressEntity addressEntity, int i) {
            list.get(defaultIndex).setIs_default("0");
            addressEntity.setIs_default("1");
            defaultIndex=i;
    }

    public interface OnClickListener {
        void onClick(AddressEntity address);
    }

    public void setOnOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_telephone)
        TextView tvTelephone;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.tv_editor)
        TextView tvEditor;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.tv_select)
        TextView tvSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
