package com.smsintercept.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsMessage;

import com.smsintercept.db.MyDBHelper;
import com.smsintercept.utils.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class SmsDao {
    private Context mContext;
    private MyDBHelper dbHelper;
    public SmsDao(Context context) {
        this.mContext = context;
        dbHelper = new MyDBHelper(this.mContext, Constant.DB_NAME, null, Constant.DB_NEW_VERSION);
    }

    public void insert(Sms sms) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("phoneNum", sms.phoneNum);
        values.put("smsContent", sms.smsContent);
        values.put("time", sms.time);
        db.insert(Constant.TABLE_SMS, null, values);
        values = null;
        db.close();
    }

    public List<Sms> queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Sms> smsList = new ArrayList<Sms>();
        Cursor cursor = db.query(Constant.TABLE_SMS, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do{
                Sms sms = new Sms();
                sms.id = cursor.getInt(cursor.getColumnIndex("id"));
                sms.phoneNum = cursor.getString(cursor.getColumnIndex("phoneNum"));
                sms.smsContent = cursor.getString(cursor.getColumnIndex("smsContent"));
                sms.time = cursor.getString(cursor.getColumnIndex("time"));
                smsList.add(sms);
            } while(cursor.moveToNext());
        }
        return smsList;
    }

    public void delete(Sms sms) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(Constant.TABLE_SMS, "id=?", new String[]{String.valueOf(sms.id)});
        db.close();
    }
}
