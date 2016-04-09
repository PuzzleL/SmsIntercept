package com.smsintercept.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.smsintercept.R;
import com.smsintercept.activity.KeywordActivity;
import com.smsintercept.entity.KeyWord;
import com.smsintercept.entity.KeyWordDao;

import java.util.List;

/**
 * Created by Blacktea on 2016/3/26.
 */
public class KeywordAdapter extends BaseAdapter {
    private List<KeyWord> dataList;
    private Context mContext;
    private LayoutInflater inflater;

    public KeywordAdapter(Context context, List<KeyWord> data) {
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
        final KeyWord keyword = dataList.get(i);
        ViewHolder holder = null;
        if(view == null) {
            view = inflater.inflate(R.layout.list_item_phone_or_keyword, null);
            holder = new ViewHolder();
            holder.tvKeyword = (TextView) view.findViewById(R.id.tv_content);
            holder.btnDelete = (Button) view.findViewById(R.id.btn_delete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(keyword != null) {
            holder.tvKeyword.setText(keyword.keyWord);
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new KeyWordDao(KeywordAdapter.this.mContext).detele(keyword);
                    KeywordAdapter.this.dataList.remove(i);
                    KeywordActivity activity = (KeywordActivity)mContext;
                    activity.getHandler().obtainMessage(KeywordActivity.DATA_CHANGE).sendToTarget();
                }
            });
        }
        return view;
    }

    class ViewHolder {
        public TextView tvKeyword;
        public Button btnDelete;
    }
}
