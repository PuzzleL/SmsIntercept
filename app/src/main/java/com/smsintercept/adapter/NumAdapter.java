package com.smsintercept.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.smsintercept.R;
import com.smsintercept.activity.PhoneActivity;
import com.smsintercept.entity.Phone;
import com.smsintercept.entity.PhoneDao;

import java.util.List;

/**
 * Created by Blacktea on 2016/3/25.
 */
public class NumAdapter extends BaseAdapter {
    private List<Phone> dataList;
    private Context mContext;
    private LayoutInflater inflater;

    public NumAdapter(Context context, List<Phone> data) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Phone phone = dataList.get(i);
        ViewHolder holder = null;
        if(view == null) {
            view = inflater.inflate(R.layout.list_item_phone_or_keyword, null);
            holder = new ViewHolder();
            holder.tvNum = (TextView) view.findViewById(R.id.tv_content);
            holder.btnDelete = (Button) view.findViewById(R.id.btn_delete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(phone != null) {
            holder.tvNum.setText(phone.phoneNum);
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new PhoneDao(NumAdapter.this.mContext).detele(phone);
                    NumAdapter.this.dataList.remove(i);
                    PhoneActivity activity = (PhoneActivity)mContext;
                    activity.getHandler().obtainMessage(PhoneActivity.DATA_CHANGE).sendToTarget();
                }
            });
        }
        return view;
    }

    class ViewHolder {
        public TextView tvNum;
        public Button btnDelete;
    }
}
