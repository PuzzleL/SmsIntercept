package com.smsintercept.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.smsintercept.R;
import com.smsintercept.adapter.SmsAdapter;
import com.smsintercept.common.BaseActivity;
import com.smsintercept.entity.Sms;
import com.smsintercept.entity.SmsDao;
import com.smsintercept.service.SmsInterceptService;
import com.smsintercept.utils.Constant;
import com.smsintercept.utils.SmsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MainActivity
 * @author zxl </br>
 * Date: 2016-3-21 </br>
 * @version 1.0.0 </br>
 * @since JDK1.7 </br>
 * Description: 首页Activity
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    public static final int DATA_CHANGE = 0;
    private LinearLayout lltMain, lltTitleLeft, lltTitlCenter, lltTitleRight;
    private ImageView ivTitleRight;
    private TextView tvTitle, tvSmsNum, tvInterceptModel, tvRemoveFromRubish, tvDeleteFromRubish, tvCancle;
    private ListView lvSms;
    private SmsAdapter smsAdapter;
    private List<Sms> smsList = new ArrayList<Sms>();
    private SharedPreferences sp;
    private PopupWindow popWindowSmsLongClick;
    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSms();
        loadInterceptModel();
        startService(new Intent(this, SmsInterceptService.class));
    }

    @Override
    protected void onDestroy() {
        startService(new Intent(this, SmsInterceptService.class));
        super.onDestroy();
    }

    @Override
    protected void initView() {
        lltMain = (LinearLayout) findViewById(R.id.llt_main);
        lltTitleLeft = (LinearLayout) findViewById(R.id.llt_title_left);
        lltTitleLeft.setVisibility(View.GONE);
        lltTitlCenter = (LinearLayout) findViewById(R.id.llt_title_center);
        lltTitleRight = (LinearLayout) findViewById(R.id.llt_title_right);
        ivTitleRight = (ImageView) findViewById(R.id.iv_title_right);
        ivTitleRight.setImageResource(R.drawable.button_setting);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSmsNum = (TextView) findViewById(R.id.tv_sms_num);
        tvInterceptModel = (TextView) findViewById(R.id.tv_intercept_model);
        View pm = LayoutInflater.from(this).inflate(R.layout.popwindow_sms_longclick, null);
        popWindowSmsLongClick = new PopupWindow(pm, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindowSmsLongClick.setTouchable(true);
        tvRemoveFromRubish = (TextView) pm.findViewById(R.id.tv_remove_from_rubish);
        tvDeleteFromRubish = (TextView) pm.findViewById(R.id.tv_delete_from_rubish);
        tvCancle = (TextView) pm.findViewById(R.id.tv_cancle);
        lvSms = (ListView) findViewById(R.id.lv_sms);
    }

    @Override
    protected void initDate() {
        sp = getSharedPreferences(Constant.SETTING_PREFERENCES, MODE_PRIVATE);
        smsAdapter = new SmsAdapter(MainActivity.this, smsList);
        lvSms.setAdapter(smsAdapter);
        tvTitle.setText("首页");
    }

    @Override
    protected void initListener() {
        lltTitleRight.setOnClickListener(this);
        tvRemoveFromRubish.setOnClickListener(this);
        tvDeleteFromRubish.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
        lvSms.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                popWindowSmsLongClick.showAtLocation(new View(MainActivity.this), Gravity.BOTTOM, 0, 0);
                lltMain.setBackgroundColor(Color.GRAY);
                //保存长按的item下标，在PopupWindow中可以通过此下标对相应item进行操作
                selectedItem = i;
                return true;
            }
        });
        lvSms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //进入短信详情
                Intent intent = new Intent(MainActivity.this, SmsDetailActivity.class);
                intent.putExtra("sms", smsList.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.llt_title_right: //进入设置
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_remove_from_rubish: //移回手机短信的操作
                SmsUtil.insertIntoSmsBox(MainActivity.this, smsList.get(selectedItem));
                new SmsDao(MainActivity.this).delete(smsList.get(selectedItem));
                smsList.remove(selectedItem);
                //提示Activity数据发生改变，刷新ListView
                handler.obtainMessage(DATA_CHANGE).sendToTarget();
                popWindowSmsLongClick.dismiss();
                lltMain.setBackgroundColor(Color.WHITE);
                Toast.makeText(MainActivity.this, "移出成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_delete_from_rubish: //移回手机短信的操作
                new SmsDao(MainActivity.this).delete(smsList.get(selectedItem));
                smsList.remove(selectedItem);
                //提示Activity数据发生改变，刷新ListView
                handler.obtainMessage(DATA_CHANGE).sendToTarget();
                popWindowSmsLongClick.dismiss();
                lltMain.setBackgroundColor(Color.WHITE);
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cancle: //取消PopupWindow
                popWindowSmsLongClick.dismiss();
                lltMain.setBackgroundColor(Color.WHITE);
                break;
        }
    }

    private void loadSms() {
        //开启子线程获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                smsList.clear();
                smsList.addAll(new SmsDao(MainActivity.this).queryAll());
                handler.obtainMessage(MainActivity.DATA_CHANGE).sendToTarget();
            }
        }).start();
    }

    private void loadInterceptModel() {
        //获取用户设置拦截模式
        int model = sp.getInt("interceptModel", Constant.INTERCEPT_MODEL_NONE);
        switch (model) {
            case Constant.INTERCEPT_MODEL_BLACK:
                tvInterceptModel.setText("黑名单拦截");
                break;
            case Constant.INTERCEPT_MODEL_WHITE:
                tvInterceptModel.setText("白名单通过");
                break;
            case Constant.INTERCEPT_MODEL_KEYWORD:
                tvInterceptModel.setText("关键字拦截");
                break;
            case Constant.INTERCEPT_MODEL_AI:
                tvInterceptModel.setText("智能拦截");
                break;
            case Constant.INTERCEPT_MODEL_NONE:
                tvInterceptModel.setText("无拦截");
                break;
        }
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATA_CHANGE:
                    tvSmsNum.setText("已拦截短信" + smsList.size() + "条");
                    smsAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public Handler getHandler() {
        return handler;
    }
}
