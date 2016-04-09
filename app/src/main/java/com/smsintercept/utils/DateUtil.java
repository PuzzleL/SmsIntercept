package com.smsintercept.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class DateUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getNowDateString(){
        return sdf.format(new Date());
    }
    public static Date covertStringToDate(String dateString){
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String covertDateToString(Date date) {
        return sdf.format(date);
    }
}
