package ru.sport.liverpool.Players_fragment_classes;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import ru.sport.liverpool.R;


public class MainPlayersFragment extends Fragment {
    View view;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    Button btn, btn2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_players, container, false);
        fragment = new PlayersFragment();
        fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frameSmall,fragment);
        ft.commit();
        btn = view.findViewById(R.id.button);
        btn2 = view.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new PlayersFragment();
                fm = getChildFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frameSmall,fragment);
                ft.commit();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new StatsFragment();
                fm = getChildFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frameSmall,fragment);
                ft.commit();
            }
        });


        return view;
    }

}