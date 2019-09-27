package com.example.kala.no4database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //super(context, name, factory, version);
        super(context, "food.db", null, 1);
    }

    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table food(id integer primary key autoincrement,name varchar(20),ingredients varchar(100))");
    }

    @Override
    //软件版本号发生改变时调用
    //(SQLiteDatabase db, int oldVersion, int newVersion)
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("alter table food add foodIngredients varchar(30) NULL");
    }

}
