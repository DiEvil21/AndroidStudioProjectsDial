package ru.sport.betanoapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PageFragment extends Fragment {
    String url;
    View view;
    TextView tv_about;
    ImageButton downButton;
    ImageView imageView;
    ProgressBar progressBar;
    FrameLayout content_frame;
    int id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_page, container, false);
        downButton = view.findViewById(R.id.downBtn);
        tv_about = view.findViewById(R.id.tv_about);
        imageView = view.findViewById(R.id.imageView);
        progressBar = view.findViewById(R.id.progress_bar);
        content_frame = view.findViewById(R.id.content_frame);
        content_frame.setVisibility(View.INVISIBLE);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }else id = 0;

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_about.getVisibility() == View.VISIBLE) {
                    tv_about.setVisibility(View.GONE);
                    downButton.setRotation(180);
                }else {
                    tv_about.setVisibility(View.VISIBLE);
                    downButton.setRotation(0);
                }

            }
        });
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/BetanoApp/text.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray arr = new JSONArray(myResponse);
                            JSONObject json = new JSONObject(arr.get(id).toString());
                            tv_about.setText(json.optString("text"));
                            Glide.with(imageView.getContext())
                                    .load(json.optString("img"))
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            progressBar.setVisibility(View.GONE);
                                            content_frame.setVisibility(View.VISIBLE);
                                            return false;
                                        }
                                    })
                                    .into(imageView);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });

            }
        });
    }


}