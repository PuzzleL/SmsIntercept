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
import com.smsintercept.adapter.KeywordAdapter;
import com.smsintercept.common.BaseActivity;
import com.smsintercept.entity.KeyWord;
import com.smsintercept.entity.KeyWordDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blacktea on 2016/3/26.
 */
public class KeywordActivity extends BaseActivity {
    public static final int DATA_CHANGE = 0;
    private int intentType;
    private LinearLayout lltTitleLeft;
    private TextView tvTitle;
    private ImageView ivTitleLeft;
    private EditText etNumOrKeyword;
    private Button btnAdd;
    private ListView lvContent;
    private List<KeyWord> keywordList = new ArrayList<KeyWord>();
    private KeywordAdapter keywordAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_or_keyword);
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
        keywordAdapter = new KeywordAdapter(this, keywordList);
        lvContent.setAdapter(keywordAdapter);
        ivTitleLeft.setImageResource(R.drawable.button_back);
        tvTitle.setText("关键字列表");
        load();
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
                KeyWord keyWord = new KeyWord();
                keyWord.keyWord = content;
                if(TextUtils.isEmpty(content)) {
                    Toast.makeText(KeywordActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else if(keywordList.contains(keyWord)) {
                    Toast.makeText(KeywordActivity.this, "关键字已存在", Toast.LENGTH_SHORT).show();
                } else {
                    add(content);
                    etNumOrKeyword.setText("");
                    Toast.makeText(KeywordActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.llt_title_left:
                finish();
                break;
        }
    }

    private void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                keywordList.clear();
                keywordList.addAll(new KeyWordDao(KeywordActivity.this).queryAll());
                handler.obtainMessage(DATA_CHANGE).sendToTarget();
            }
        }).start();
    }

    private void add(String content) {
        final KeyWord keyword = new KeyWord();
        keyword.keyWord = content;
        new Thread(new Runnable() {
            @Override
            public void run() {
                new KeyWordDao(KeywordActivity.this).insert(keyword);
                keywordList.add(keyword);
                handler.obtainMessage(DATA_CHANGE).sendToTarget();
            }
        }).start();
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA_CHANGE:
                    keywordAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public Handler getHandler(){
        return handler;
    }
}
