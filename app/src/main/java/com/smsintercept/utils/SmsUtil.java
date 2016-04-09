package com.smsintercept.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.smsintercept.entity.Sms;

import java.util.Date;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class SmsUtil {
    public static final String TAG = "SmsUtil";
    //获取接收的Message数组，短信内容过长是会分出多个Message
    public static SmsMessage[] getMessageFromIntent(Intent intent) {
        SmsMessage[] messages = null;
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            messages = new SmsMessage[pdus.length];
            for(int i = 0; i < messages.length; i++){
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                Log.d(TAG, "message[i].orginalAddress" + messages[i].getOriginatingAddress());
            }
        }
        return messages;
    }

    public static void insertIntoSmsBox(Context context, Sms sms) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("address", sms.phoneNum);
        values.put("body", sms.smsContent);
        resolver.insert(Uri.parse("content://sms/inbox"), values);
    }

    //将长短信数组合并成一条
    public static Sms combineMessagesToSmsEntity(SmsMessage[] messages) {
        Sms sms = new Sms();
        sms.smsContent = "";
        for(SmsMessage message : messages) {
            sms.smsContent += message.getMessageBody();
        }
        sms.phoneNum = messages[0].getOriginatingAddress();
        if(sms.phoneNum.startsWith("+86")) {
            //去掉"+86"前缀
            sms.phoneNum = sms.phoneNum.substring(3);
        }
        sms.time = DateUtil.covertDateToString(new Date(messages[0].getTimestampMillis()));
        return sms;
    }
}
