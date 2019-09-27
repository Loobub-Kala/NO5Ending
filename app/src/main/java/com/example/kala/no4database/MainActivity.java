package com.example.kala.no4database;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    public Query query = new Query();
    public Increase increase = new Increase();
    public Modify modify = new Modify();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_query:
                    //mTextMessage.setText(R.string.data_query);
                    transaction.replace(R.id.content, query);
                    transaction.commit();
                    return true;
                case R.id.navigation_increase:
                    //mTextMessage.setText(R.string.data_increase);
                    transaction.replace(R.id.content, increase);
                    transaction.commit();
                    return true;
                case R.id.navigation_modify:
                    //mTextMessage.setText(R.string.data_modify);
                    transaction.replace(R.id.content, modify);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    private void setDefalultFragment() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, query);
        transaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setDefalultFragment();
    }

}
