package com.smsintercept.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.smsintercept.R;
import com.smsintercept.common.BaseActivity;
import com.smsintercept.utils.Constant;

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout lltTitleLeft;
    private ImageView ivTitleLeft;
    private TextView tvTitle;
    private Spinner spInterceptModel;
    private LinearLayout lltBlack, lltWhite, lltKeyword;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initDate();
        initListener();
    }

    @Override
    protected void initView() {
        lltTitleLeft = (LinearLayout) findViewById(R.id.llt_title_left);
        ivTitleLeft = (ImageView) findViewById(R.id.iv_title_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        spInterceptModel = (Spinner) findViewById(R.id.sp_intercept_model);
        lltBlack = (LinearLayout) findViewById(R.id.llt_black);
        lltWhite = (LinearLayout) findViewById(R.id.llt_white);
        lltKeyword = (LinearLayout) findViewById(R.id.llt_keyword);
    }

    @Override
    protected void initDate() {
        tvTitle.setText("设置");
        ivTitleLeft.setImageResource(R.drawable.button_back);
        sp = getSharedPreferences(Constant.SETTING_PREFERENCES, MODE_PRIVATE);
        spInterceptModel.setSelection(sp.getInt("interceptModel",0));
    }

    @Override
    protected void initListener() {
       spInterceptModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               sp.edit().putInt("interceptModel",i).commit();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        lltBlack.setOnClickListener(this);
        lltWhite.setOnClickListener(this);
        lltKeyword.setOnClickListener(this);
        lltTitleLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch(view.getId()) {
            case R.id.llt_black:
                intent = new Intent(SettingActivity.this, PhoneActivity.class);
                intent.putExtra(Constant.PHONE_OR_KEYWORD_INTENT_TYPE,Constant.PHONE_OR_KEYWORD_TYPE_BLACK);
                startActivity(intent);
                break;
            case R.id.llt_white:
                intent = new Intent(SettingActivity.this, PhoneActivity.class);
                intent.putExtra(Constant.PHONE_OR_KEYWORD_INTENT_TYPE, Constant.PHONE_OR_KEYWORD_TYPE_WHITE);
                startActivity(intent);
                break;
            case R.id.llt_keyword:
                intent = new Intent(SettingActivity.this, KeywordActivity.class);
                startActivity(intent);
                break;
            case R.id.llt_title_left:
                finish();
                break;
        }
    }
}
