package ru.sport.reviewclubs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
//http://159.69.90.204/api/

    private ClubsApi clubsApi;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progressBar);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://159.69.90.204/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        clubsApi = retrofit.create(ClubsApi.class);


        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FrameLayout frame = findViewById(R.id.frame_main);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/ReviewClubs/img/background_horizontal.png")
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            frame.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } else {
            // In portrait
            Glide.with(MainActivity.this)
                    .load("http://159.69.90.204/api/ReviewClubs/img/background.png")
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            frame.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
        getClubs();
    }
    private void getClubs() {
        Call<List<Clubs>> call = clubsApi.getClubs();
        //Call<List<Post>> call = jsonPlaceHolderApi.getPosts(new Integer[]{2,3,6},null,null);

        call.enqueue(new Callback<List<Clubs>>() {
            @Override
            public void onResponse(Call<List<Clubs>> call, Response<List<Clubs>> response) {
                progressBar.setVisibility(View.GONE);
                List<Clubs> clubs = response.body();
                // создаем адаптер
                ClubsAdapter adapter = new ClubsAdapter(MainActivity.this, clubs);
                // устанавливаем для списка адаптер
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Clubs>> call, Throwable t) {

            }
        });
    }
}