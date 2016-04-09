package com.smsintercept.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smsintercept.R;
import com.smsintercept.entity.Sms;

import java.util.List;

/**
 * Created by Blacktea on 2016/3/26.
 */
public class SmsAdapter extends BaseAdapter {
    private List<Sms> dataList;
    private Context mContext;
    private LayoutInflater inflater;

    public SmsAdapter(Context context, List<Sms> data) {
        this.mContext = context;
        this.dataList = data;
        this.inflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList ==  null ? null : dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Sms sms = dataList.get(i);
        ViewHolder holder = null;
        if(view == null) {
            view = inflater.inflate(R.layout.list_item_sms, null);
            holder = new ViewHolder();
            holder.tvPhoneNum = (TextView) view.findViewById(R.id.tv_phone_num);
            holder.tvContent = (TextView) view.findViewById(R.id.tv_content);
            holder.tvTime = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(sms != null) {
            holder.tvPhoneNum.setText(sms.phoneNum);
            holder.tvContent.setText(sms.smsContent);
            holder.tvTime.setText(sms.time);
        }
        return view;
    }

    class ViewHolder {
        public TextView tvPhoneNum;
        public TextView tvContent;
        public TextView tvTime;
    }
}
