package com.sport.marselappj.ui.matchs;

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
import com.sport.marselappj.databinding.FragmentMatchsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MatchsFragment extends Fragment {
    ArrayList<MatchsState> states = new ArrayList<MatchsState>();
    private FragmentMatchsBinding binding;
    ProgressBar mProgressBar;
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matchs, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }

    private void setInitialData(String img,String name, String date, String country, String score) {

        states.add(new MatchsState(img,date,country,name,score));
    }


    void run() throws IOException {
        System.out.println("---------------------------------");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/MarselApp/matchs.json")
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
                        System.out.println(myResponse);
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);

                            for (int i = 0 ; jsonObject.getJSONArray("stats").length() > i; i++){
                                System.out.println(jsonObject.getJSONArray("stats").get(i));
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("stats").get(i)));
                                setInitialData(json.optString("img"),
                                        json.optString("name"),
                                        json.optString("date"),
                                        json.optString("country"),
                                        json.optString("score"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        MatchsStateAdapter adapter = new MatchsStateAdapter(view.getContext(), states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);
                        mProgressBar.setVisibility(View.GONE);




                    }

                });

            }
        });
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}