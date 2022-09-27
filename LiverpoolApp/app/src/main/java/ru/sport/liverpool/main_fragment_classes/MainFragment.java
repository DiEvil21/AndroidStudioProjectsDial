package ru.sport.liverpool.main_fragment_classes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.sport.liverpool.DownloadImageTask;
import ru.sport.liverpool.R;


public class MainFragment extends Fragment {
    View view;
    String url;
    TextView name, h2, info, lastMtextL, lastMtextR,score;
    ImageView logo, lastMimageR, lastMimageL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        url = "http://159.69.90.204/api/liverpoolApp/mainf.json";
        name = view.findViewById(R.id.textView6);
        h2 = view.findViewById(R.id.textView7);
        info = view.findViewById(R.id.textView15);
        lastMtextL = view.findViewById(R.id.textView9);
        lastMtextR = view.findViewById(R.id.textView10);
        lastMimageL = view.findViewById(R.id.imageView3);
        lastMimageR = view.findViewById(R.id.imageView4);
        score = view.findViewById(R.id.textView12);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }










    void run() throws IOException {
        System.out.println("---------------------------------");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("----------------------");
                final String myResponse = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(myResponse);
                        System.out.println("-------------------------------------------------");
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            System.out.println(jsonObject.optString("name"));
                            name.setText(jsonObject.optString("name"));
                            h2.setText(jsonObject.optString("about"));
                            info.setText(jsonObject.optString("info"));
                            JSONObject json = new JSONObject(jsonObject.optString("lastmatch"));
                            lastMtextL.setText(json.optString("name1"));
                            lastMtextR.setText(json.optString("name2"));
                            score.setText(json.optString("score"));
                            new DownloadImageTask(lastMimageL).execute(json.optString("img1"));
                            new DownloadImageTask(lastMimageR).execute(json.optString("img2"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });

            }
        });
    }





}