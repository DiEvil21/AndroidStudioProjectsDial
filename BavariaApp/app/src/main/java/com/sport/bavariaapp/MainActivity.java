package com.sport.bavariaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.sport.bavariaapp.match_fragment.MatchFragment;
import com.sport.bavariaapp.news_fragment.NewsFragment;
import com.sport.bavariaapp.players_fragment.PlayersFragment;
import com.sport.bavariaapp.result_fragment.ResultFragment;
import com.sport.bavariaapp.stats_fragment.StatsFragment;

public class MainActivity extends AppCompatActivity {
    FrameLayout frame;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new MainFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }


    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main:
                fragment = new MainFragment();
                break;
            case R.id.btn_news:
                fragment = new NewsFragment();
                break;
            case R.id.btn_matchs:
                fragment = new MatchFragment();
                break;
            case R.id.btn_results:
                fragment = new ResultFragment();
                break;
            case R.id.btn_players:
                fragment = new PlayersFragment();
                break;
            case R.id.btn_stats:
                fragment = new StatsFragment();
                break;


        }
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }
}