package com.smsintercept.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsMessage;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;
import com.smsintercept.InterceptStrategy.IStrategy;
import com.smsintercept.InterceptStrategy.StrategyHolder;
import com.smsintercept.R;
import com.smsintercept.activity.SmsDetailActivity;
import com.smsintercept.entity.Sms;
import com.smsintercept.entity.SmsDao;
import com.smsintercept.utils.Constant;
import com.smsintercept.utils.DateUtil;
import com.smsintercept.utils.NotificationUtil;
import com.smsintercept.utils.SmsUtil;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class SmsInterceptReceiver extends BroadcastReceiver {
    private Context mContext;

    public SmsInterceptReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO 完成短信拦截
        mContext = context;
        SharedPreferences sp = context.getSharedPreferences(Constant.SETTING_PREFERENCES, Context.MODE_PRIVATE);
        int index = sp.getInt("interceptModel", Constant.INTERCEPT_MODEL_NONE);
        IStrategy strategy = StrategyHolder.STRATEGYS[index];
        if (strategy == null) {
            return;
        }
        SmsMessage[] messages = SmsUtil.getMessageFromIntent(intent);
        final Sms sms = SmsUtil.combineMessagesToSmsEntity(messages);
        if (strategy.execIntercept(context, sms)) {
            new SmsDao(mContext).insert(sms);
            Intent intent2 = new Intent(mContext, SmsDetailActivity.class);
            intent2.putExtra("sms", sms);
            final PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
            AsyncHttpResponseHandler resHandler = new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                    try {
                        String newContent = new String(bytes, "utf-8");
                        newContent = newContent.substring(newContent.indexOf('[') + 1, newContent.indexOf(']'));
                        Log.d("onSuccess", newContent);
                        NotificationUtil.sendNotification(mContext, "拦截到一条短信", R.mipmap.ic_launcher,
                                                            "以下为拦截短信关键内容", newContent, pIntent);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                    try {
                        Log.d("onFailure", new String(bytes, "utf-8"));
                        NotificationUtil.sendNotification(mContext, "拦截到一条短信", R.mipmap.ic_launcher,
                                                         "以下为拦截短信关键内容", sms.smsContent, pIntent);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            };
            new ShowApiRequest("http://route.showapi.com/920-1", "17472", "7586a6f2ba004f9580163b0a6f7cd5b5")
                    .setResponseHandler(resHandler)
                    .addTextPara("title", "")
                    .addTextPara("content", sms.smsContent)
                    .addTextPara("num", "5")
                    .post();


            Log.d("InterceptReceiver", "Intercepted");
            abortBroadcast();
        }

    }
}
