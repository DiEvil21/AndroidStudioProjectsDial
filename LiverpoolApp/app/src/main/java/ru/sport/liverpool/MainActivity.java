package ru.sport.liverpool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import ru.sport.liverpool.News_fragment_classes.NewsFragment;
import ru.sport.liverpool.Players_fragment_classes.MainPlayersFragment;
import ru.sport.liverpool.Players_fragment_classes.PlayersFragment;
import ru.sport.liverpool.Results_fragment_classes.ResultFragment;
import ru.sport.liverpool.main_fragment_classes.MainFragment;

public class MainActivity extends AppCompatActivity {
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

     public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton5:
                fragment = new MainFragment();
                break;
            case R.id.imageButton6:
                fragment = new NewsFragment();
                break;
            case R.id.imageButton7:
                fragment = new ResultFragment();
                break;
            case R.id.imageButton8:
                fragment = new MainPlayersFragment();
                break;
        }
         fm = getSupportFragmentManager();
         ft = fm.beginTransaction();
         ft.replace(R.id.frame,fragment);
         ft.commit();
     }
}