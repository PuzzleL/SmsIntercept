package com.smsintercept.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smsintercept.db.MyDBHelper;
import com.smsintercept.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Blacktea on 2016/3/24.
 */
public class KeyWordDao {
    private Context mContext;
    private MyDBHelper dbHelper;
    public KeyWordDao(Context context) {
        this.mContext = context;
        dbHelper = new MyDBHelper(this.mContext, Constant.DB_NAME, null, Constant.DB_NEW_VERSION);
    }

    public void insertKeyWord(KeyWord[] keyWords) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        for(KeyWord item : keyWords) {
            values.put("keyword", item.keyWord);
            db.insert("key_word", null, values);
            values.clear();
        }
        db.close();
    }

    public ArrayList<KeyWord> queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();
        Cursor cursor = db.query(Constant.TABLE_KEYWORD,null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                KeyWord keyWord = new KeyWord();
                keyWord.keyWord = cursor.getString(cursor.getColumnIndex("keyWord"));
                keyWordList.add(keyWord);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return keyWordList;
    }

    public void insert(KeyWord keyword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("keyWord", keyword.keyWord);
        db.insert(Constant.TABLE_KEYWORD, null, values);
        values = null;
        db.close();
    }

    public void detele(KeyWord keyword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(Constant.TABLE_KEYWORD, "keyWord=?", new String[]{keyword.keyWord});
        db.close();
    }
}
