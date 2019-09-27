package com.example.kala.no4database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyDataBase {

    //static ArrayList<Food> foods = new ArrayList<Food>();

    /*public MyDataBase(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from food order by id", null);
    }*/

    //查找：通过姓名
    public static Food dataQuery(SQLiteDatabase db, String na) {
        Cursor cursor = db.rawQuery("select * from food where name = ?", new String[]{na});
        //存在数据返回true
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String ingredients = cursor.getString(cursor.getColumnIndex("ingredients"));
            return new Food(id, name, ingredients);
        }
        cursor.close();
        return null;
    }

    //添加数据
    public static boolean dataIncrease(SQLiteDatabase db, Food f) {
        try {
            db.execSQL("insert into food(name,ingredients) values(?,?)", new String[]{f.getName(), f.getIngredients()});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //删除数据
    public static boolean dataDelete(SQLiteDatabase db, String na) {
        try {
            db.execSQL("delete from food where name = ?", new String[]{na});
            Log.i("qqqqq",na);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //修改数据
    public static boolean dataModify(SQLiteDatabase db, Food f, String data) {
        try {
            db.execSQL("update food set name = ?, ingredients = ? where name = ?", new String[]{f.getName(),f.getIngredients(), data});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //查询记录条数
    public static long getCount(SQLiteDatabase db) {
        //如果SQL语句是“SELECT * FROM person”，那么可以cursor.getCount()
        Cursor cursor = db.rawQuery("select count (*) from food", null);
        cursor.moveToFirst();
        long result = cursor.getLong(0);
        cursor.close();
        return result;
    }

    //列表
    public static ArrayList<Food> dataList(SQLiteDatabase db) {
        ArrayList<Food> foods = new ArrayList<Food>();
        Cursor cursor = db.rawQuery("select * from food order by id", null);
        //存在数据返回true
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String ingredients = cursor.getString(cursor.getColumnIndex("ingredients"));
            foods.add(new Food(id, name, ingredients));
        }
        cursor.close();
        return foods;
    }

}
