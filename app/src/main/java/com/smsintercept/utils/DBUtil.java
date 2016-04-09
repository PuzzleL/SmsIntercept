package com.smsintercept.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.smsintercept.db.MyDBHelper;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class DBUtil {
    public static SQLiteDatabase getDB(Context context){
        MyDBHelper myDBHelper = new MyDBHelper(context, Constant.DB_NAME, null, Constant.DB_NEW_VERSION);
        return myDBHelper.getWritableDatabase();
    }
}
