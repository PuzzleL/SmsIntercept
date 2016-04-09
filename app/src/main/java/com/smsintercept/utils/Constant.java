package com.smsintercept.utils;

/**
 * Created by Blacktea on 2016/3/23.
 */
public class Constant {
    public static final String DB_NAME = "SmsIntercept.db";
    public static final int DB_FIRST_VERSION = 1;
    public static final int DB_NEW_VERSION = 1;

    public static final String TABLE_KEYWORD = "key_word";
    public static final String TABLE_SMS = "sms";
    public static final String TABLE_PHONE = "phone";

    public static final String SETTING_PREFERENCES = "setting";

    public static final String PHONE_OR_KEYWORD_INTENT_TYPE = "type";
    public static final int PHONE_OR_KEYWORD_TYPE_DEFAULT = 0;
    public static final int PHONE_OR_KEYWORD_TYPE_BLACK = 1;
    public static final int PHONE_OR_KEYWORD_TYPE_WHITE = 2;
    public static final int PHONE_OR_KEYWORD_TYPE_KEYWORD = 3;

    public static final int INTERCEPT_MODEL_NONE = 0;
    public static final int INTERCEPT_MODEL_BLACK = 1;
    public static final int INTERCEPT_MODEL_WHITE = 2;
    public static final int INTERCEPT_MODEL_KEYWORD = 3;
    public static final int INTERCEPT_MODEL_AI = 4;

}
