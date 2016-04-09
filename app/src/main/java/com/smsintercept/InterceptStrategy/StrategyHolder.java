package com.smsintercept.InterceptStrategy;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class StrategyHolder {
    public static final IStrategy[] STRATEGYS = new IStrategy[]{null, new BlackListStrategy(), new WhiteListStrategy(), new KeyWordStrategy(), null};
}
