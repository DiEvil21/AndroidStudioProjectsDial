package com.sport.ligaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sport.ligaapp.news_fragment_classes.NewsFragment;
import com.sport.ligaapp.result_fragment_classes.ResultFragment;
import com.sport.ligaapp.stats_fragment_classes.StatsFragment;
import com.sport.ligaapp.table_fragment_classes.TableFragment;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    Button btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        fragment = new NewsFragment();
        fm = getSupportFragmentManager();
        btn1.setTextColor(0xff90b5f0);
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                fragment = new NewsFragment();
                btn1.setTextColor(0xff90b5f0);
                btn2.setTextColor(0xffffffff);
                btn3.setTextColor(0xffffffff);
                btn4.setTextColor(0xffffffff);

                break;
            case R.id.button2:
                fragment = new TableFragment();
                btn2.setTextColor(0xff90b5f0);
                btn1.setTextColor(0xffffffff);
                btn3.setTextColor(0xffffffff);
                btn4.setTextColor(0xffffffff);
                break;
            case R.id.button3:
                btn3.setTextColor(0xff90b5f0);
                btn2.setTextColor(0xffffffff);
                btn1.setTextColor(0xffffffff);
                btn4.setTextColor(0xffffffff);
                fragment = new ResultFragment();

                break;
            case R.id.button4:
                btn4.setTextColor(0xff90b5f0);
                btn2.setTextColor(0xffffffff);
                btn3.setTextColor(0xffffffff);
                btn1.setTextColor(0xffffffff);
                fragment = new StatsFragment();
                break;
        }
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }
}