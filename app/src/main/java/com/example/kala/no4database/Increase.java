package com.example.kala.no4database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Increase extends Fragment {

    //用于焦点集中，隐藏键盘
    private TextView textHeader;

    private EditText et_name;
    private EditText et_ingredients;
    private Button button;

    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;

    private Food food;

    public static final int RC_CHOOSE_PHOTO = 2;
    public boolean isSuccess = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_increase, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Toast.makeText(Increase.this.getContext(),"gfdgdfgfdgfdg",Toast.LENGTH_SHORT).show();

        bind();
        myDBHelper = new MyDBHelper(this.getContext(), "food.db", null, 1);
        db = myDBHelper.getWritableDatabase();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button获得焦点，隐藏键盘
                textHeader.setFocusable(true);
                textHeader.setFocusableInTouchMode(true);
                textHeader.requestFocus();
                textHeader.requestFocusFromTouch();

                if ("".equals(et_name.getText().toString())) {
                    Toast.makeText(Increase.this.getContext(), "Error:菜名未输入", Toast.LENGTH_LONG).show();
                } else {
                    //查询菜是否已存在
                    Food f = MyDataBase.dataQuery(db, et_name.getText().toString());
                    if (f == null) {
                        food = new Food();
                        food.setName(et_name.getText().toString());
                        if ("".equals(et_ingredients.getText().toString())) {
                            Toast.makeText(Increase.this.getContext(), "Warning:无食材", Toast.LENGTH_SHORT).show();
                            food.setIngredients("无(未知)");
                        } else {
                            food.setIngredients(et_ingredients.getText().toString());
                        }


                        //插入,i为保存次数
                        int i = 0;
                        while (!MyDataBase.dataIncrease(db, food)) {
                            i++;
                            Toast.makeText(Increase.this.getContext(), "Error:重试第 " + i + " 次", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(Increase.this.getContext(), "Success:录入成功！", Toast.LENGTH_LONG).show();
                        et_name.setText("");
                        et_ingredients.setText("");
                    }else {
                        Toast.makeText(Increase.this.getContext(), "Error:录入失败，菜品已存在！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void bind() {
        //控件初始化
        textHeader =getView().findViewById(R.id.increase_test);
        et_name = getView().findViewById(R.id.increase_et);
        et_ingredients = getView().findViewById(R.id.increase_et2);
        button = getView().findViewById(R.id.increase_btn);
    }
}
