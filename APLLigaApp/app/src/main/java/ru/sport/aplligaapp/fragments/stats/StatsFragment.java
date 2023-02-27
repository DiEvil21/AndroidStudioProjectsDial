package ru.sport.aplligaapp.fragments.stats;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import ru.sport.aplligaapp.R;
import ru.sport.aplligaapp.fragments.wall.WallState;
import ru.sport.aplligaapp.fragments.wall.WallStateAdapter;


public class StatsFragment extends Fragment {
    View view;
    ArrayList<StatsInfo> states = new ArrayList<StatsInfo>();
    ProgressBar bar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        bar = view.findViewById(R.id.progress_bar);
        try {
            sendNudes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void setData(String st0, String st1,String st2,String st3,String st4,String st5,String st6) {

        states.add(new StatsInfo(st0,st1,st2,st3,st4,st5,st6));
    }


    void sendNudes() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/APLLigaApp/stats.json")
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
                            states = new ArrayList<StatsInfo>();
                            JSONObject jsonObject = new JSONObject(myResponse);
                            for (int i = 0 ; jsonObject.getJSONArray("stats").length() > i; i++){
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("stats").get(i)));
                                setData(
                                        json.optString("st0"),
                                        json.optString("st1"),
                                        json.optString("st2"),
                                        json.optString("st3"),
                                        json.optString("st4"),
                                        json.optString("st5"),
                                        json.optString("st6")
                                );



                            }
                            RecyclerView recyclerView = view.findViewById(R.id.list);
                            // создаем адаптер
                            StatsInfoAdapter adapter = new StatsInfoAdapter(view.getContext(), states);
                            // устанавливаем для списка адаптер
                            recyclerView.setAdapter(adapter);
                            bar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }




                    }
                });

            }
        });
    }





}