package ru.sport.liverpool.Players_fragment_classes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import ru.sport.liverpool.R;


public class PlayersFragment extends Fragment {
    ArrayList<PlayersState> states = new ArrayList<PlayersState>();
    View view;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_players, container, false);
        url = "http://159.69.90.204/api/liverpoolApp/players.json";
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setInitialData(String name, String role, String country, String img, String flag,String info) {

        states.add(new PlayersState(name,role,country,img,flag,info));
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
                            System.out.println(jsonObject.getJSONArray("players").length());
                            for (int i = 0 ; jsonObject.getJSONArray("players").length() > i; i++){
                                System.out.println("--------------");
                                System.out.println(jsonObject.getJSONArray("players").get(i));
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("players").get(i)));
                                setInitialData(json.optString("name"),
                                        json.optString("role"),
                                        json.optString("country"),
                                        json.optString("img"),
                                        json.optString("flag"),
                                        json.optString("info"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        PlayersStateAdapter adapter = new PlayersStateAdapter(view.getContext(), states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }



}