package com.smsintercept.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smsintercept.db.MyDBHelper;
import com.smsintercept.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class PhoneDao {
    private Context mContext;
    private MyDBHelper dbHelper;
    public PhoneDao(Context context) {
        this.mContext = context;
        dbHelper = new MyDBHelper(this.mContext, Constant.DB_NAME, null, Constant.DB_NEW_VERSION);
    }
    public  List<Phone> queryPhoneByType(int phoneType) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Phone> phoneList = new ArrayList<Phone>();
        Cursor cursor = db.query("phone", null, "type=?", new String[]{String.valueOf(phoneType)}, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                Phone phone = new Phone();
                phone.phoneNum = cursor.getString(cursor.getColumnIndex("phoneNum"));
                phone.type = cursor.getInt(cursor.getColumnIndex("type"));
                phoneList.add(phone);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return phoneList;
    }

    public void insert(Phone phone) {
        if(findPhoneByNum(phone.phoneNum) == null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("phoneNum", phone.phoneNum);
            values.put("type", phone.type);
            db.insert(Constant.TABLE_PHONE, null, values);
            values = null;
            db.close();
        } else {
            update(phone);
        }
    }

    private void update(Phone phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("phoneNum", phone.phoneNum);
        values.put("type", phone.type);
        db.update(Constant.TABLE_PHONE, values, "phoneNum=?", new String[]{phone.phoneNum});
        values = null;
        db.close();
    }

    private Phone findPhoneByNum(String phoneNum) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Phone phone = null;
        Cursor cursor = db.query("phone", null, "phoneNum=?", new String[]{phoneNum}, null, null, null);
        if(cursor.moveToFirst()) {
            phone = new Phone();
            phone.phoneNum = cursor.getString(cursor.getColumnIndex("phoneNum"));
            phone.type = cursor.getInt(cursor.getColumnIndex("type"));
        }
        cursor.close();
        db.close();
        return phone;
    }

    public boolean isPhoneExist(Phone phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("phone", null, "phoneNum=? and type=?", new String[]{phone.phoneNum, String.valueOf(phone.type)}, null, null, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public void detele(Phone phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(Constant.TABLE_PHONE, "phoneNum=?", new String[]{phone.phoneNum});
        db.close();
    }
}
