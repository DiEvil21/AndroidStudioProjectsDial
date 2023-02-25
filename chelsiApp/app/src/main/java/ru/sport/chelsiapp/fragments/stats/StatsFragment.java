package ru.sport.chelsiapp.fragments.stats;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class StatsFragment extends Fragment {
    View view;
    ArrayList<StatsState> states = new ArrayList<StatsState>();
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String myUrl = prefs.getString("url", null);
        url = myUrl + "stat.json";

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }
    private void setInitialData(String st1,String st2,String st3,String st4,String st5,String st6,String st7,String st8,String sti, String count) {

        states.add(new StatsState(st1,st2,st3,st4,st5,st6,st7,st8,sti,count));
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
                            System.out.println(myResponse);
                            JSONObject jsonObject = new JSONObject(myResponse);

                            for (int i = 0 ; jsonObject.getJSONArray("stats").length() > i; i++){
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("stats").get(i)));
                                setInitialData(json.optString("st1"),
                                        json.optString("st2"),
                                        json.optString("st3"),
                                        json.optString("st4"),
                                        json.optString("st5"),
                                        json.optString("st6"),
                                        json.optString("st7"),
                                        json.optString("st8"),
                                        json.optString("st0"),
                                        String.valueOf(i+1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        StatsStateAdapter adapter = new StatsStateAdapter(view.getContext(), states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }


}