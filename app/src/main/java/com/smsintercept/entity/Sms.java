package com.smsintercept.entity;

import java.io.Serializable;

/**
 * Created by Blacktea on 2016/3/23.
 */
public class Sms implements Serializable{
    public int id;
    public String phoneNum;
    public String smsContent;
    public String time;

    public Sms(){}

    public Sms(int id, String phoneNum, String smsContent, String time) {
        this.id = id;
        this.phoneNum = phoneNum;
        this.smsContent = smsContent;
        this.time = time;
    }

    public Sms(String phoneNum, String smsContent, String time) {
        this.phoneNum = phoneNum;
        this.smsContent = smsContent;
        this.time = time;
    }
}
