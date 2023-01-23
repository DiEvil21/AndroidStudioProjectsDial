package com.sport.marselappj.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.marselappj.R;
import com.sport.marselappj.databinding.FragmentStatsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StatsFragment extends Fragment {
    ArrayList<StatsState> states = new ArrayList<StatsState>();
    View view;
    ProgressBar mProgressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }



    private void setInitialData(String st0,String st1,String st2,String st3,String st4,String st5,String st6) {

        states.add(new StatsState(st0,st1,st2,st3,st4,st5,st6));
    }

    void run() throws IOException {
        System.out.println("---------------------------------");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/MarselApp/stat.json")
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
                        try {
                            System.out.println(myResponse);
                            JSONObject jsonObject = new JSONObject(myResponse);
                            for (int i = 0 ; jsonObject.getJSONArray("stats").length() > i; i++){
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("stats").get(i)));
                                setInitialData(
                                        json.optString("st0"),
                                        json.optString("st1"),
                                        json.optString("st2"),
                                        json.optString("st3"),
                                        json.optString("st4"),
                                        json.optString("st5"),
                                        json.optString("st6"));
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
                        mProgressBar.setVisibility(View.GONE);





                    }

                });

            }
        });
    }



}