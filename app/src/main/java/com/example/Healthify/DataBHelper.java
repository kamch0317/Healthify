package com.example.Healthify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBHelper extends SQLiteOpenHelper {
    public DataBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(name TEXT primary key, contact TEXT, age TEXT, password TEXT, kg TEXT, weight TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");


    }

    public Boolean insertuserdata(String name, String contact, Integer age, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("age", age);
        contentValues.put("password", password);
        long result = DB.insert("Userdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean checkusername(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from  Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkpass(String name, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ? and password=? ", new String[]{name, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getdata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor data = DB.rawQuery("Select * from  Userdetails where name = ?", new String[]{name});
        if (data.getCount() > 0) {
            if (data != null) {
                data.moveToFirst();
            }
        }
        return data;
    }

    public Boolean updatename(String name, String contact, Integer age, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("age", age);
        contentValues.put("password", password);
        Cursor data = DB.rawQuery("Select * from  Userdetails where name = ?", new String[]{name});

        if (data.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public String getcontact(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from  Userdetails where name = ?", new String[]{name});
        String values="";
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            values = cursor.getString(1);
        }

        return values;
    }

    public String getage(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from  Userdetails where name = ?", new String[]{name});
        String values="";
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            values = cursor.getString(2);
        }

        return values;
    }

    public String getpassword(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from  Userdetails where name = ?", new String[]{name});
        String values="";
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            values = cursor.getString(3);
        }

        return values;
    }

//    public String getkg(String name){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("Select * from  UserInfo where name = ?", new String[]{name});
//        String values="";
//        if(cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            values = cursor.getString(4);
//        }
//
//        return values;
//    }





}
