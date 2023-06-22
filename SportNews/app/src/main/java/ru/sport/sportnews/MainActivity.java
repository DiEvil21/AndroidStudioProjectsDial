package ru.sport.sportnews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NewsViewModel newsViewModel;
    private ListView newsListView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout backgroundLinearLayout = findViewById(R.id.main_back);
        String imageUrl = "http://159.69.90.204/api/SportNews/background.png"; // Замените ссылку на вашу URL-ссылку на изображение
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                backgroundLinearLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                // Обработка ошибки загрузки изображения
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // Действия при подготовке загрузки изображения
            }
        });
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        newsListView = findViewById(R.id.newsListView);
        newsAdapter = new NewsAdapter(this, newsViewModel.getNewsItems().getValue());
        newsListView.setAdapter(newsAdapter);

        newsViewModel.getNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(List<NewsItem> newsItems) {
                newsAdapter.setNewsItems(newsItems);
                newsAdapter.notifyDataSetChanged();
            }
        });


        Button footballButton = findViewById(R.id.footballButton);
        Button hockeyButton = findViewById(R.id.hockeyButton);
        Button boxingButton = findViewById(R.id.boxingButton);
        newsViewModel.loadFootballNews();
        footballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsViewModel.loadFootballNews();
            }
        });

        hockeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsViewModel.loadHockeyNews();
            }
        });

        boxingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsViewModel.loadBoxingNews();
            }
        });
    }
}
