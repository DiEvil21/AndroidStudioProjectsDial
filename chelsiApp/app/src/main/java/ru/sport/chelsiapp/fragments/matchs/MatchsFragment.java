package ru.sport.chelsiapp.fragments.matchs;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class MatchsFragment extends Fragment {
    SharedPreferences mSettings;
    ArrayList<MatchState> states = new ArrayList<MatchState>();
    String url;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matchs, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String myUrl = prefs.getString("url", null);
        url = myUrl + "matchs.json";
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void setInitialData(String name, String role, String country, String img) {

        states.add(new MatchState(name, role, country, img));
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
                        System.out.println(myResponse);
                        System.out.println("-------------------------------------------------");
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            for (int i = 0; jsonObject.getJSONArray("stats").length() > i; i++) {
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("stats").get(i)));
                                setInitialData(json.optString("name"),
                                        json.optString("role"),
                                        json.optString("date"),
                                        json.optString("img"));
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


                    }

                });

            }
        });
    }


}