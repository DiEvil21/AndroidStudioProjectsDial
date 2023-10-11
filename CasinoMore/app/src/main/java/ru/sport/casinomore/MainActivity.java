package ru.sport.casinomore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ru.sport.casinomore.fragments.FirstFragment;
import ru.sport.casinomore.fragments.SecondFragment;
import ru.sport.casinomore.fragments.ThirdFragment;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout background = findViewById(R.id.main_background);
        String imageUrl = "http://159.69.90.204/api/CasinoMore/background.png";
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                background.setBackground(new BitmapDrawable(getResources(), bitmap  ));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        BottomNavigationView menu = findViewById(R.id.bottom_navigation);
        menu.setItemIconTintList(null);
        Fragment selectedFragment = null;
        selectedFragment = new FirstFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.okno, selectedFragment).commit();
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();
                if (itemId == R.id.nav_first) {
                    selectedFragment = new FirstFragment();
                } else if (itemId == R.id.nav_second) {
                    selectedFragment = new SecondFragment();
                } else if (itemId == R.id.nav_third) {
                    selectedFragment = new ThirdFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.okno, selectedFragment).commit();
                return true;
            }
        });


    }
}