package com.smsintercept.InterceptStrategy;

import android.content.Context;
import android.telephony.SmsMessage;

import com.smsintercept.entity.KeyWord;
import com.smsintercept.entity.KeyWordDao;
import com.smsintercept.entity.Sms;

import java.util.ArrayList;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class KeyWordStrategy implements IStrategy {
    @Override
    public boolean execIntercept(Context context, Sms message) {
        KeyWordDao keyWordDao = new KeyWordDao(context);
        ArrayList<KeyWord> keyWords = keyWordDao.queryAll();
        String smsContent = message.smsContent;
        if(keyWords.isEmpty()) {
            return false;
        } else {
            for(KeyWord item : keyWords) {
                if(smsContent.contains(item.keyWord)) {
                    return true;
                }
            }
        }
        return false;
    }
}
