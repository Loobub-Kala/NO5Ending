package com.example.kala.no4database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWord;
    private Button login;
    private String username = "kala";
    private String password = "123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().equals(username)&&passWord.getText().toString().equals(password)){
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(StartActivity.this, "用户名或者密码错误",Toast.LENGTH_SHORT).show();
                    userName.setText("");
                    passWord.setText("");
                }
            }
        });
    }


}
