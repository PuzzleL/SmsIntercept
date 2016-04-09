package com.smsintercept.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.smsintercept.R;
import com.smsintercept.activity.MainActivity;
import com.smsintercept.receiver.SmsInterceptReceiver;

public class SmsInterceptService extends Service {
    public static final String TAG = "SmsInterceptService";
    private boolean isReceiverRegist = false;
    private SmsInterceptReceiver smsInterceptReceiver;
    public SmsInterceptService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.smsInterceptReceiver = new SmsInterceptReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        registerReceiver(smsInterceptReceiver, filter);
        this.isReceiverRegist = true;
        Log.d(TAG, "Regist SmsInterceptReceiver");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("SmsIntercept")
                .setContentText("contentText");
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(pendingintent);
        startForeground(0x111, builder.getNotification());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(this.smsInterceptReceiver != null && this.isReceiverRegist) {
            unregisterReceiver(this.smsInterceptReceiver);
            this.smsInterceptReceiver = null;
            this.isReceiverRegist = false;
            Log.d(TAG, "UnRegist SmsInterceptReceiver");
            Toast.makeText(this, "拦截服务已关闭，谢谢使用", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
