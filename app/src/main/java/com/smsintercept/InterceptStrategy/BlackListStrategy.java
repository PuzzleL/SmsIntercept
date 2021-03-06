package com.smsintercept.InterceptStrategy;

import android.content.Context;
import android.telephony.SmsMessage;

import com.smsintercept.entity.Phone;
import com.smsintercept.entity.PhoneDao;
import com.smsintercept.entity.Sms;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class BlackListStrategy implements IStrategy {
    @Override
    public boolean execIntercept(Context context, Sms message) {
        Phone phone = new Phone(message.phoneNum, Phone.TYPE_BLCAK);
        if(new PhoneDao(context).isPhoneExist(phone)){
            return true;
        }
        return false;
    }
}
