package com.example.uttampujari;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlDatabse extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Information.db";
    public static final String TABLE_NAME = "Demo_Information";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRST_NAME";
    public static final String COL_3 = "LAST_NAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "IMAGE";

    public SqlDatabse(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRST_NAME TEXT ,LAST_NAME TEXT , EMAIL TEXT ,IMAGE TEXT )" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String fname,String lname,String email,String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,fname);
        contentValues.put(COL_3,lname);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,image);
        Long res = db.insert(TABLE_NAME,null,contentValues);
        if (res == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor readAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from Demo_Information order by ID desc";
        Cursor cursor = db.rawQuery(qry,null);
        return cursor;
    }

    public void deleteCourse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_1 +" = ? ",new String[]{String.valueOf(id)});
        db.close();
    }
}
