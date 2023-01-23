package com.sport.marselappj.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.marselappj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends Fragment {
    ArrayList<NewsState> states = new ArrayList<NewsState>();
    View view;
    ProgressBar mProgressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news, container, false);;
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);


        return view;
    }



    public void setInitialData(String title, String date, String text, String image){

        states.add(new NewsState (title,date,text,image));


    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/MarselApp/news.json")
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
                            states = new ArrayList<NewsState>();
                            JSONArray arr = new JSONArray(myResponse);
                            System.out.println(myResponse);
                            JSONObject jsonObject = arr.getJSONObject(1);
                            System.out.println("---------------------------------------------------");
                            System.out.println(arr.getJSONObject(0));
                            for (int i = 0 ; i < arr.length() ; i++) {
                                jsonObject = new JSONObject(arr.get(i).toString());
                                System.out.println(jsonObject.optString("tittle"));
                                setInitialData(jsonObject.optString("tittle"),
                                        jsonObject.optString("date"),
                                        jsonObject.optString("text"),
                                        jsonObject.optString("img"));
                                System.out.println("повезло----------------------------");


                            }
                            RecyclerView recyclerView = view.findViewById(R.id.list);
                            // создаем адаптер
                            NewsStateAdapter adapter = new NewsStateAdapter(view.getContext(), states);
                            // устанавливаем для списка адаптер
                            recyclerView.setAdapter(adapter);
                            mProgressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }




                    }
                });

            }
        });
    }



}