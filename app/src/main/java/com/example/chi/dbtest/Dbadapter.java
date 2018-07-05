package com.example.chi.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class Dbadapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_BIRTH = "birth";
    private static final String TABLE_NAME = "member";
    private Dbhelper mDbHelper;
    private SQLiteDatabase mdb;
    private final Context mCtx;
    private ContentValues values;

    public Dbadapter(Context mCtx) {
        this.mCtx = mCtx;
        open();
    }
    public void open(){
        mDbHelper = new Dbhelper(mCtx);
        mdb = mDbHelper.getWritableDatabase();
        Log.i("DB=",mdb.toString());
    }
    public long createContact(String name, String phone, String email, String birth){
        try{
            values = new ContentValues();
            values.put(KEY_NAME, name);
            values.put(KEY_PHONE, phone);
            values.put(KEY_EMAIL, email);
            values.put(KEY_BIRTH, birth);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mCtx,"新增成功!", Toast.LENGTH_SHORT).show();

        }
        return mdb.insert(TABLE_NAME,null,values);
    }
    public Cursor listContacts(){
        Cursor mCursor = mdb.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME,KEY_PHONE, KEY_EMAIL, KEY_BIRTH},
                null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor queryById(int item_id){
        Cursor  mCursor = mdb.query(TABLE_NAME, new String[] {KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_BIRTH},
                KEY_ID + "=" + item_id, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }
    public long updateContacts(int id, String name, String phone, String email, String birth){
        long update = 0;
        try{
            //將資料丟到contentValues
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, name);
            values.put(KEY_PHONE, phone);
            values.put(KEY_EMAIL, email);
            values.put(KEY_BIRTH, birth);
            update = mdb.update(TABLE_NAME, values, "_id=" + id,null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mCtx, "成功更新一筆資料!",Toast.LENGTH_LONG).show();
        }
        return update;
    }
    public boolean deleteContacts(int id){
        String[] args = {Integer.toString(id)};
        mdb.delete(TABLE_NAME, "_id= ?",args);
        return true;
    }
}
