package com.example.kala.no4database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Query extends Fragment {

    //用于焦点集中，隐藏键盘
    private FrameLayout frameLayout;

    //控件定义
    private EditText editText;
    private Button button;
    private TextView textView;
    private ListView listView;

    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;

    private ArrayList<Food> foods = new ArrayList<Food>();
    //ListView的自定义适配器
    public MyAdapter aa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        aa = new MyAdapter(this.getActivity(), foods);
        return inflater.inflate(R.layout.fragment_query, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bind();
        myDBHelper = new MyDBHelper(this.getContext(), "food.db", null, 1);
        db = myDBHelper.getWritableDatabase();

        foods.clear();
        foods.addAll(MyDataBase.dataList(db));
        //Toast.makeText(Query.this.getContext(), "Food:"+foods.get(0).getName(), Toast.LENGTH_SHORT).show();
        //aa = new MyAdapter(this.getActivity(), foods);

        listView.setAdapter(aa);
        textView.setText("Sum: "+MyDataBase.getCount(db));
        //aa.notifyDataSetChanged();//更新数据

        //InputMethodManager imm =(InputMethodManager) this.getContext().getSystemService()
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) Query.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("".equals(editText.getText().toString())) {
                    Toast.makeText(Query.this.getContext(), "Error:您未输入", Toast.LENGTH_LONG).show();
                    foods.clear();
                    foods.addAll(MyDataBase.dataList(db));
                    aa.notifyDataSetChanged();//更新数据
                    textView.setText("Sum: "+MyDataBase.getCount(db));
                } else {
                    Food f = MyDataBase.dataQuery(db, editText.getText().toString());
                    if (f == null) {
                        f = new Food();
                        f.setId(0);
                        f.setName("查无此菜");
                        f.setIngredients("请仔细检查您的输入信息");
                        Toast.makeText(Query.this.getContext(), "Warning:查无此菜", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Query.this.getContext(), "Success:查询成功", Toast.LENGTH_SHORT).show();
                    }
                    foods.clear();
                    foods.add(f);
                    aa.notifyDataSetChanged();//更新数据
                    textView.setText("Sum: "+MyDataBase.getCount(db));
                }
            }
        });
    }

    public void bind() {
        //控件初始化
        frameLayout =getView().findViewById(R.id.Query_Layout);
        editText = getView().findViewById(R.id.query_et);
        button = getView().findViewById(R.id.query_btn);
        textView = getView().findViewById(R.id.query_sum);
        listView = getView().findViewById(R.id.query_list);

    }


}
