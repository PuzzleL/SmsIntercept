package com.smsintercept.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Blacktea on 2016/3/23.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public String TAG = "MyDBHelper";

    public static final String CREATE_TABLE_SMS = "create table sms ("
                                                        + "id integer primary key autoincrement,"
                                                        + "phoneNum text,"
                                                        + "smsContent text,"
                                                        + "time text)";

    public static final String CREATE_PHONE_LIST = "create table phone ("
                                                        + "phoneNum text primary key,"
                                                        + "type integer)";

    public static final String CREATE_TABLE_KEYWORD = "create table key_word ("
                                                            + "keyWord text primary key)";

    private Context mContext;
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SMS);
        db.execSQL(CREATE_PHONE_LIST);
        db.execSQL(CREATE_TABLE_KEYWORD);
        Log.d(TAG, "DataBase created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG,"DataBase upgrade.");
    }
}
