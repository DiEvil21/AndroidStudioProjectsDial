package ru.sport.footballdictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DicActivity extends AppCompatActivity {
    ArrayList<Data> data = new ArrayList<Data>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic);

        int orientation = getResources().getConfiguration().orientation;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linear = findViewById(R.id.linear_dic);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Glide.with(DicActivity.this)
                    .load("http://159.69.90.204/api/DictionaryApp/img/pole_horizontal.png")
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            linear.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } else {
            // In portrait
            Glide.with(DicActivity.this)
                    .load("http://159.69.90.204/api/DictionaryApp/img/pole.png")
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            linear.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
        try {
            getR();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setda(String word, String meaning) {

        data.add(new Data(word,meaning));
    }


    void getR() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/DictionaryApp/termins.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(myResponse);

                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            for (int i = 0 ; jsonObject.getJSONArray("words").length() > i; i++){
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("words").get(i)));
                                setda(json.optString("word"),
                                        json.optString("meaning"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = findViewById(R.id.list);

                        // создаем адаптер
                        DataAdapter adapter = new DataAdapter(DicActivity.this, data);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }


}