package ru.sport.historicalmvvmfootball;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class QuotesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    QuotesViewModel quotesViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        recyclerView = findViewById(R.id.list);
        quotesViewModel =new ViewModelProvider(this).get(QuotesViewModel.class);


        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout layout = findViewById(R.id.quotes_linear);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(QuotesActivity.this)
                    .load("http://159.69.90.204/api/FootballMvvmFacts/background_horizontal.jpg")
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            layout.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } else {
            // In portrait
            Glide.with(QuotesActivity.this)
                    .load("http://159.69.90.204/api/FootballMvvmFacts/background.jpg")
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            layout.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        quotesViewModel.getQuotes().observe(this, new Observer<List<QuotesData>>() {
            @Override
            public void onChanged(List<QuotesData> quotesData) {
                QuotesAdapter adapter = new QuotesAdapter(QuotesActivity.this, quotesData);
                // устанавливаем для списка адаптер
                recyclerView.setAdapter(adapter);
            }
        });
    }
}