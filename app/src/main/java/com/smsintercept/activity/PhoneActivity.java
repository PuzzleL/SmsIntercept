package com.smsintercept.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smsintercept.R;
import com.smsintercept.adapter.NumAdapter;
import com.smsintercept.common.BaseActivity;
import com.smsintercept.entity.Phone;
import com.smsintercept.entity.PhoneDao;
import com.smsintercept.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blacktea on 2016/3/25.
 */
public class PhoneActivity extends BaseActivity {
    public static final int DATA_CHANGE = 0;
    private int intentType;
    private LinearLayout lltTitleLeft;
    private TextView tvTitle;
    private ImageView ivTitleLeft;
    private EditText etNumOrKeyword;
    private Button btnAdd;
    private ListView lvContent;
    private List<Phone> phoneList = new ArrayList<Phone>();
    private NumAdapter numAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_or_keyword);
        intentType = getIntent().getIntExtra(Constant.PHONE_OR_KEYWORD_INTENT_TYPE, Constant.PHONE_OR_KEYWORD_TYPE_DEFAULT);
        initView();
        initDate();
        initListener();
    }

    @Override
    protected void initView() {
        lltTitleLeft = (LinearLayout) findViewById(R.id.llt_title_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivTitleLeft = (ImageView) findViewById(R.id.iv_title_left);
        etNumOrKeyword = (EditText) findViewById(R.id.et_num_or_keyword);
        btnAdd = (Button) findViewById(R.id.btn_add);
        lvContent = (ListView) findViewById(R.id.lv_content);
    }

    @Override
    protected void initDate() {
        numAdapter = new NumAdapter(this, phoneList);
        ivTitleLeft.setImageResource(R.drawable.button_back);
        lvContent.setAdapter(numAdapter);
        if(intentType == Constant.PHONE_OR_KEYWORD_TYPE_BLACK) {
            tvTitle.setText("黑名单列表");
            loadPhone(Constant.PHONE_OR_KEYWORD_TYPE_BLACK);
        } else if (intentType == Constant.PHONE_OR_KEYWORD_TYPE_WHITE) {
            tvTitle.setText("白名单列表");
            loadPhone(Constant.PHONE_OR_KEYWORD_TYPE_WHITE);
        }
    }

    @Override
    protected void initListener() {
        btnAdd.setOnClickListener(this);
        lltTitleLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                String content = etNumOrKeyword.getText().toString();
                Phone phone = new Phone();
                phone.phoneNum = content;
                if(TextUtils.isEmpty(content)) {
                    Toast.makeText(PhoneActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else if(phoneList.contains(phone)) {
                    Toast.makeText(PhoneActivity.this, "号码已存在", Toast.LENGTH_SHORT).show();
                } else {
                    add(content);
                    etNumOrKeyword.setText("");
                    Toast.makeText(PhoneActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.llt_title_left:
                finish();
                break;
        }
    }

    private void loadPhone(final int phoneType) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                phoneList.clear();
                phoneList.addAll(new PhoneDao(PhoneActivity.this).queryPhoneByType(phoneType));
                handler.obtainMessage(DATA_CHANGE).sendToTarget();
            }
        }).start();
    }

    private void add(String content) {
        switch (intentType) {

            case Constant.PHONE_OR_KEYWORD_TYPE_BLACK:
                final Phone phoneBlack = new Phone(content, Phone.TYPE_BLCAK);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new PhoneDao(PhoneActivity.this).insert(phoneBlack);
                        phoneList.add(phoneBlack);
                        handler.obtainMessage(DATA_CHANGE).sendToTarget();
                    }
                }).start();
                break;
            case Constant.PHONE_OR_KEYWORD_TYPE_WHITE:
                final Phone phoneWhite = new Phone(content, Phone.TYPE_WHITE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new PhoneDao(PhoneActivity.this).insert(phoneWhite);
                        phoneList.add(phoneWhite);
                        handler.obtainMessage(DATA_CHANGE).sendToTarget();
                    }
                }).start();
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA_CHANGE:
                    numAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public Handler getHandler(){
        return handler;
    }
}
