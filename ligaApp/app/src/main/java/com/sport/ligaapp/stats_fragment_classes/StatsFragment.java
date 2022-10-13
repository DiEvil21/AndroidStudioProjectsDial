package com.sport.ligaapp.stats_fragment_classes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.ligaapp.R;

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
    View view;
    ArrayList<StatsState> states = new ArrayList<StatsState>();
    TextView st1, st2, st3, st4, st5, st6, st7, st8, st0;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        url = "http://159.69.90.204/api/LigaApp/stat.json";
        st0 = view.findViewById(R.id.stcount);
        st1 = view.findViewById(R.id.st1);
        st2 = view.findViewById(R.id.st2);
        st3 = view.findViewById(R.id.st3);
        st4 = view.findViewById(R.id.st4);
        st5 = view.findViewById(R.id.st5);
        st6 = view.findViewById(R.id.st6);
        st7 = view.findViewById(R.id.st7);
        st8 = view.findViewById(R.id.st8);

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }

    private void setInitialData(String st1, String st2, String st3, String st4, String st5, String st6, String st7, String st8, String sti, String count) {

        states.add(new StatsState(st1, st2, st3, st4, st5, st6, st7, st8, sti, count));
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
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            st0.setText(" ");
                            st1.setText(jsonObject.optString("st0"));
                            st2.setText(jsonObject.optString("st1"));
                            st3.setText(jsonObject.optString("st2"));
                            st4.setText(jsonObject.optString("st3"));
                            st5.setText(jsonObject.optString("st4"));
                            st6.setText(jsonObject.optString("st5"));
                            st7.setText(jsonObject.optString("st6"));

                            System.out.println(jsonObject.getJSONArray("teams").length());
                            for (int i = 0; jsonObject.getJSONArray("teams").length() > i; i++) {
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("teams").get(i)));
                                setInitialData(
                                        json.optString("st0"),
                                        json.optString("st1"),
                                        json.optString("st2"),
                                        json.optString("st3"),
                                        json.optString("st4"),
                                        json.optString("st5"),
                                        json.optString("st6"),
                                        json.optString("st7"),
                                        json.optString("st8"),

                                        String.valueOf(i + 1));
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