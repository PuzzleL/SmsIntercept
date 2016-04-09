package com.smsintercept.InterceptStrategy;

import android.content.Context;
import android.telephony.SmsMessage;

import com.smsintercept.entity.Sms;

/**
 * ClassName: IStrategy
 * @author zxl </br>
 * Date: 2016-3-21 </br>
 * @version 1.0.0 </br>
 * @since JDK1.7 </br>
 * Description: 抽象执行拦截短信策略的接口
 */
public interface IStrategy {
    public boolean execIntercept(Context context, Sms message);
}
