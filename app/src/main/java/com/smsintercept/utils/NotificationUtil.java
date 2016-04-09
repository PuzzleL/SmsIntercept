package com.smsintercept.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

/**
 * Created by Blacktea on 2016/3/26.
 */
public class NotificationUtil {
    public static void sendNotification(Context context, String ticker, int icon, String title, String content, PendingIntent pIntent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker(ticker)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pIntent);
        Notification notification = builder.getNotification();
        manager.notify(1, notification);
    }
    public static String getPropertyNotification(String originalContent) {
        String start, middle, end;
        String separator = "****";
        int length = originalContent.length();
        if(originalContent.length() > 9) {
            start = originalContent.substring(0,length / 9);
            middle = originalContent.substring(length* 3 / 9, length* 4 / 9);
            end = originalContent.substring(length * 8 / 9, length);
            return start + separator + middle + separator + end;
        } else {
            return originalContent;
        }
    }

}
