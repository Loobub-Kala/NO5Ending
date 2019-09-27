package com.example.kala.no4database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Food> fs;

    public MyAdapter(Context context, ArrayList<Food> fs) {
        this.context = context;
        this.fs = fs;
        Log.d("qqq","1");
    }


    @Override
    public int getCount() {
        return fs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            //Log.d("qqq","2");
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);
           // Log.d("qqq","3");
       }
            /*ImageView iv = (ImageView) convertView.findViewById(R.id.list_pic);
            iv.setImageResource(Integer.parseInt(fs.get(position).getPic()));*/
        TextView tv = (TextView) convertView.findViewById(R.id.list_tv);
        Log.d("qqq","5 "+fs.get(position).getName()+" "+fs.get(position).getIngredients());
        tv.setText(fs.get(position).getName());
        TextView tv2 = (TextView) convertView.findViewById(R.id.list_tv2);
        tv2.setText(fs.get(position).getIngredients());
        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
