package com.example.kala.no4database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Modify extends Fragment {

    //用于焦点集中，隐藏键盘
    private TextView textHeader;

    private Button modify_btn;
    private Button modify_btnDelete;

    //隐藏区
    private EditText modify_etQuery;
    private Button modify_btnCheck;
    private TextView modify_tvIn1;
    private EditText modify_etIn2;
    private Button modify_btnSave;

    //点击“修改\删除”按钮前隐藏区
    private LinearLayout modify_hide1;
    //列表初始不可见——隐藏区
    private ListView modify_hide2;
    //搜索后的修改部分隐藏区
    private LinearLayout modify_hide3;

    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;
    private ArrayList<Food> foods = new ArrayList<Food>();
    //ListView的自定义适配器
    public MyAdapter aa;

    //判断按钮点击为 “修改”还是“删除”
    public enum TypeButton {
        MODIFY,//修改
        DELETE,//删除
        EMPTY;//无选择
    }

    public TypeButton typeButton = TypeButton.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        aa = new MyAdapter(this.getActivity(), foods);

        return inflater.inflate(R.layout.fragment_modify, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bind();
        myDBHelper = new MyDBHelper(this.getContext(), "food.db", null, 1);
        db = myDBHelper.getWritableDatabase();
        modify_hide2.setAdapter(aa);
        modify_etQuery.setText("");
        modify_etIn2.setText("");

        //点击“修改数据”按钮
        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_etQuery.setText("");
                modify_hide1.setVisibility(View.VISIBLE);
                modify_hide2.setVisibility(View.GONE);
                modify_hide3.setVisibility(View.GONE);
                modify_etQuery.setHint("请输入菜名修改数据");
                typeButton = TypeButton.MODIFY;
            }
        });

        //点击“删除数据”按钮
        modify_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_etQuery.setText("");
                modify_hide1.setVisibility(View.VISIBLE);
                modify_hide2.setVisibility(View.GONE);
                modify_hide3.setVisibility(View.GONE);
                modify_etQuery.setHint("请输入菜名删除数据");
                typeButton = TypeButton.DELETE;
            }
        });

        //点击“确认”按钮
        modify_btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button获得焦点，隐藏键盘
                textHeader.setFocusable(true);
                textHeader.setFocusableInTouchMode(true);
                textHeader.requestFocus();
                textHeader.requestFocusFromTouch();

                if ("".equals(modify_etQuery.getText().toString())) {
                    Toast.makeText(Modify.this.getContext(), "Error:您未输入", Toast.LENGTH_LONG).show();
                    foods.clear();
                    aa.notifyDataSetChanged();//更新数据
                } else {
                    Food f = MyDataBase.dataQuery(db, modify_etQuery.getText().toString());
                    if (f == null) {
                        f = new Food();
                        f.setId(0);
                        f.setName("查无此菜");
                        f.setIngredients("请仔细检查您的输入信息");
                        Toast.makeText(Modify.this.getContext(), "Warning:查无此菜", Toast.LENGTH_LONG).show();

                        modify_hide3.setVisibility(View.GONE);
                        modify_hide2.setVisibility(View.VISIBLE);
                        foods.clear();
                        foods.add(f);
                        aa.notifyDataSetChanged();//更新数据
                    } else {
                        //数据存在
                        Toast.makeText(Modify.this.getContext(), "Success:查询成功", Toast.LENGTH_SHORT).show();
                        if (typeButton == TypeButton.MODIFY) {
                            //隐藏区3 设置为可见
                            modify_hide2.setVisibility(View.GONE);
                            modify_hide3.setVisibility(View.VISIBLE);
                            modify_tvIn1.setText(f.getName());
                            modify_etIn2.setText(f.getIngredients());
                        } else if (typeButton == TypeButton.DELETE) {
                            //隐藏区2 设置为可见
                            modify_hide3.setVisibility(View.GONE);
                            modify_hide2.setVisibility(View.VISIBLE);

                            foods.clear();
                            foods.add(f);
                            aa.notifyDataSetChanged();//更新数据

                            modify_hide2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    //删除，j为删除次数
                                    //Log.i("qqqqqdelete",modify_etQuery.getText().toString());
                                    int j = 0;
                                    while (!MyDataBase.dataDelete(db, modify_etQuery.getText().toString())) {
                                        j++;
                                        Toast.makeText(Modify.this.getContext(), "Error:重试第 " + j + " 次", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(Modify.this.getContext(), "Success:删除数据成功！", Toast.LENGTH_LONG).show();
                                    foods.clear();
                                    aa.notifyDataSetChanged();

                                    //删除完毕
                                    modify_hide1.setVisibility(View.GONE);
                                    modify_hide2.setVisibility(View.GONE);
                                    modify_hide3.setVisibility(View.GONE);
                                    typeButton = TypeButton.EMPTY;
                                    modify_etQuery.setText("");
                                    modify_etIn2.setText("");
                                }
                            });
                        }
                    }
                }
            }
        });

        //点击“保存数据”按钮
        modify_btnSave.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                //button获得焦点，隐藏键盘
                textHeader.setFocusable(true);
                textHeader.setFocusableInTouchMode(true);
                textHeader.requestFocus();
                textHeader.requestFocusFromTouch();

                //InputMethodManager imm = (InputMethodManager)
                //InputMethodManager imm = (InputMethodManager)
                        //getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                //菜名不可修改
                Food food = new Food();
                food.setName(modify_tvIn1.getText().toString());
                if ("".equals(modify_etIn2.getText().toString())) {
                    Toast.makeText(Modify.this.getContext(), "Warning:无食材", Toast.LENGTH_SHORT).show();
                    food.setIngredients("无(未知)");
                } else {
                    food.setIngredients(modify_etIn2.getText().toString());
                }

                //修改，i为修改次数
                int i = 0;
                while (!MyDataBase.dataModify(db, food, modify_tvIn1.getText().toString())) {
                    i++;
                    Toast.makeText(Modify.this.getContext(), "Error:重试第 " + i + " 次", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(Modify.this.getContext(), "Success:修改数据成功！", Toast.LENGTH_LONG).show();

                //修改完毕
                modify_hide1.setVisibility(View.GONE);
                modify_hide2.setVisibility(View.GONE);
                modify_hide3.setVisibility(View.GONE);
                typeButton = TypeButton.EMPTY;
                modify_etQuery.setText("");
            }
        });
    }

    public void bind() {
        textHeader =getView().findViewById(R.id.modify_test);
        modify_btn = getView().findViewById(R.id.modify_btn);
        modify_btnDelete = getView().findViewById(R.id.modify_btnDelete);
        modify_etQuery = getView().findViewById(R.id.modify_etQuery);
        modify_btnCheck = getView().findViewById(R.id.modify_btnCheck);
        modify_tvIn1 = getView().findViewById(R.id.modify_tvIn1);
        modify_etIn2 = getView().findViewById(R.id.modify_etIn2);
        modify_btnSave = getView().findViewById(R.id.modify_btnSave);
        modify_hide1 = getView().findViewById(R.id.modify_hide1);
        modify_hide2 = getView().findViewById(R.id.modify_hide2);
        modify_hide3 = getView().findViewById(R.id.modify_hide3);
    }
}
