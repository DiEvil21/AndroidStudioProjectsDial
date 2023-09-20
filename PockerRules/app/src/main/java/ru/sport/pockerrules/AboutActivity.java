package ru.sport.pockerrules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class AboutActivity extends AppCompatActivity {

    private AboutViewModel aboutViewModel;
    private AboutAdapter adapter;
    private RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        LinearLayout background = findViewById(R.id.about_back);
        String imageUrl = "http://159.69.90.204/api/PockerRules/casino_back.png";
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
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        adapter = new AboutAdapter();
        aboutViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        if (position == 0) {
            aboutViewModel.getAboutListData().observe(this, new Observer<List<AboutListData>>() {
                @Override
                public void onChanged(List<AboutListData> aboutListData) {
                    adapter.setNewsList(aboutListData);
                }
            });
        }else {
            aboutViewModel.getAboutListData2().observe(this, new Observer<List<AboutListData>>() {
                @Override
                public void onChanged(List<AboutListData> aboutListData) {
                    adapter.setNewsList(aboutListData);
                }
            });
        }
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}