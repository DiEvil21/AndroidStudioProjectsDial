package ru.soccer.russia;

import static ru.soccer.russia.ChooseActivity.APP_PREFERENCES_COMMAND;
import static ru.soccer.russia.ChooseActivity.APP_PREFERENCES_IMG;
import static ru.soccer.russia.ChooseActivity.APP_PREFERENCES_NAME;
import static ru.soccer.russia.ChooseActivity.APP_PREFERENCES_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    SharedPreferences mSettings;
    FragmentManager fm;
    FragmentTransaction ft;
    Button btn1, btn2, btn3, btn4, btn5;
    String ur;
    TextView textView;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.butMain);
        btn2 = findViewById(R.id.butMatch);
        btn3 = findViewById(R.id.butResults);
        btn4 = findViewById(R.id.butLig);
        btn5 = findViewById(R.id.butPlayers);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        Fragment frLig = new LigFragment();
        Fragment frMatch = new MatchFragment();
        Fragment frPlayers = new PlayersFragment();
        Fragment frMain = new MainFragment();
        Fragment frResults = new ResultFragment();
        mSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mSettings.getString(APP_PREFERENCES_URL,"");
        textView.setText(mSettings.getString(APP_PREFERENCES_COMMAND,""));
        new DownloadImageTask(imageView).execute(mSettings.getString(APP_PREFERENCES_IMG,""));
        fragment = new MainFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fr_place,fragment);
        ft.commit();


    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butMain:
                fragment = new MainFragment();
                btn1.setBackgroundResource(R.drawable.border_bottom);
                btn2.setBackgroundResource(R.drawable.border_none);
                btn3.setBackgroundResource(R.drawable.border_none);
                btn4.setBackgroundResource(R.drawable.border_none);
                btn5.setBackgroundResource(R.drawable.border_none);
                break;
            case R.id.butMatch:
                fragment = new MatchFragment();
                btn2.setBackgroundResource(R.drawable.border_bottom);
                btn1.setBackgroundResource(R.drawable.border_none);
                btn3.setBackgroundResource(R.drawable.border_none);
                btn4.setBackgroundResource(R.drawable.border_none);
                btn5.setBackgroundResource(R.drawable.border_none);
                break;
            case R.id.butResults:
                fragment = new ResultFragment();
                btn3.setBackgroundResource(R.drawable.border_bottom);
                btn2.setBackgroundResource(R.drawable.border_none);
                btn1.setBackgroundResource(R.drawable.border_none);
                btn4.setBackgroundResource(R.drawable.border_none);
                btn5.setBackgroundResource(R.drawable.border_none);
                break;
            case R.id.butLig:
                fragment = new LigFragment();
                btn4.setBackgroundResource(R.drawable.border_bottom);
                btn2.setBackgroundResource(R.drawable.border_none);
                btn3.setBackgroundResource(R.drawable.border_none);
                btn1.setBackgroundResource(R.drawable.border_none);
                btn5.setBackgroundResource(R.drawable.border_none);
                break;
            case R.id.butPlayers:
                fragment = new PlayersFragment();
                btn5.setBackgroundResource(R.drawable.border_bottom);
                btn2.setBackgroundResource(R.drawable.border_none);
                btn3.setBackgroundResource(R.drawable.border_none);
                btn4.setBackgroundResource(R.drawable.border_none);
                btn1.setBackgroundResource(R.drawable.border_none);
                break;

        }
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fr_place,fragment);
        ft.commit();
    }
}