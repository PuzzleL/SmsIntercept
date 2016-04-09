package com.smsintercept.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.smsintercept.service.SmsInterceptService;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收开机启动广播后直接启动拦截服务
//        Toast.makeText(context, "开机启动", Toast.LENGTH_LONG).show();
        context.startService(new Intent(context, SmsInterceptService.class));
    }
}
