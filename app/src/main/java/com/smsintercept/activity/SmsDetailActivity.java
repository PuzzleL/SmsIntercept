package com.smsintercept.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smsintercept.R;
import com.smsintercept.common.BaseActivity;
import com.smsintercept.entity.Sms;

/**
 * Created by Blacktea on 2016/3/26.
 */
public class SmsDetailActivity extends BaseActivity{
    private LinearLayout lltTitleLeft;
    private TextView tvTitle, tvTime, tvPhoneNum, tvContent;
    private ImageView ivTitleLeft;
    private Sms sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detail);
        initView();
        initDate();
        initListener();
    }

    @Override
    protected void initView() {
        lltTitleLeft = (LinearLayout) findViewById(R.id.llt_title_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);
        tvContent = (TextView) findViewById(R.id.tv_content);
        ivTitleLeft = (ImageView) findViewById(R.id.iv_title_left);
    }

    @Override
    protected void initDate() {
        tvTitle.setText("信息详情");
        ivTitleLeft.setImageResource(R.drawable.button_back);
        sms = (Sms) getIntent().getSerializableExtra("sms");
        tvTime.setText(sms.time);
        tvPhoneNum.setText(sms.phoneNum);
        tvContent.setText(sms.smsContent);
    }

    @Override
    protected void initListener() {
        lltTitleLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llt_title_left:
                finish();
                break;
        }
    }
}
