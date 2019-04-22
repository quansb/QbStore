package com.example.quansb.qbstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.AddressEntity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectAddressAdapter extends BaseAdapter {
    private ArrayList<AddressEntity> list;
    private Context context;
    private ViewHolder holder;
    private OnClickListener listener;
    private int defaultIndex;


    public SelectAddressAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<AddressEntity> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_address_layout, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
           holder= (ViewHolder) convertView.getTag();
        }

        final AddressEntity addressEntity = list.get(position);
        holder.tvName.setText(addressEntity.getConsignee());
        holder.tvTelephone.setText(addressEntity.getPhone());
        holder.tvAddress.setText(addressEntity.getAddress());

        if(addressEntity.getIs_default().equals("1")){
            holder.tvDefault.setVisibility(View.VISIBLE);
            holder.tvDefault.setText("[默认地址]");
        }else {
            holder.tvDefault.setVisibility(View.GONE);
        }

        if (addressEntity.getIsChoose().equals("0")) {
            holder.tvSelect.setBackgroundResource(R.drawable.ic_round1);
        } else if (addressEntity.getIsChoose().equals("1")) {
            defaultIndex=position;
            holder.tvSelect.setBackgroundResource(R.drawable.ic_round_red2);
        }
        holder.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUpdateUI(addressEntity,position);
                if(listener!=null){
                    listener.onClick(addressEntity,position);
                }
            }
        });
        return convertView;
    }


    private void toUpdateUI(AddressEntity addressEntity, int i) {
        list.get(defaultIndex).setIsChoose("0");
        addressEntity.setIsChoose("1");
        defaultIndex=i;
    }






    public void setOnOnClickListener(OnClickListener listener) {
        this.listener=listener;
    }
    public interface OnClickListener{
        void onClick(AddressEntity address,int position);
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_telephone)
        TextView tvTelephone;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.tv_select)
        ImageView tvSelect;
        @Bind(R.id.tv_default)
        TextView tvDefault;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
