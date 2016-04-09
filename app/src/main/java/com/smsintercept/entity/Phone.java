package com.smsintercept.entity;

/**
 * Created by Blacktea on 2016/3/23.
 */
public class Phone {
    public static final int TYPE_BLCAK = 1;
    public static final int TYPE_WHITE = 2;
    public String phoneNum;
    public int type;

    public Phone(){}

    public Phone(String phoneNum, int type) {
        this.phoneNum = phoneNum;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Phone)){
            return false;
        }
        o = (Phone)o;
        return this.phoneNum.equals(((Phone) o).phoneNum);
    }
}
