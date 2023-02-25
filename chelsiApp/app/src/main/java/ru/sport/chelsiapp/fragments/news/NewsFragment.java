package ru.sport.chelsiapp.fragments.news;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import ru.sport.chelsiapp.R;


public class NewsFragment extends Fragment {
    View view;
    String url;
    ArrayList<NewsState> states = new ArrayList<NewsState>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String myUrl = prefs.getString("url", null);
        url = myUrl + "news.json";
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = view.findViewById(R.id.list);
        // создаем адаптер
        NewsStateAdapter adapter = new NewsStateAdapter(view.getContext(), states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);


        return view;
    }


    public void setInitialData(String title, String date, String text, String image){

        states.add(new NewsState (title,date,text,image));


    }

    void run() throws IOException {

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

                final String myResponse = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            states = new ArrayList<NewsState>();
                            JSONArray arr = new JSONArray(myResponse);
                            JSONObject jsonObject = arr.getJSONObject(1);
                            for (int i = 0 ; i < arr.length() ; i++) {
                                jsonObject = new JSONObject(arr.get(i).toString());
                                System.out.println(jsonObject.optString("tittle"));
                                setInitialData(jsonObject.optString("tittle"),
                                        jsonObject.optString("date"),
                                        jsonObject.optString("text"),
                                        jsonObject.optString("img"));


                            }
                            RecyclerView recyclerView = view.findViewById(R.id.list);
                            // создаем адаптер
                            NewsStateAdapter adapter = new NewsStateAdapter(view.getContext(), states);
                            // устанавливаем для списка адаптер
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }




                    }
                });

            }
        });
    }







}