package ru.soccer.russia;

import static ru.soccer.russia.ChooseActivity.APP_PREFERENCES_NAME;
import static ru.soccer.russia.ChooseActivity.APP_PREFERENCES_URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultFragment extends Fragment {
    SharedPreferences mSettings;
    ArrayList<ResultState> states = new ArrayList<ResultState>();
    String url;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);
        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String myUrl = mSettings.getString(APP_PREFERENCES_URL,"");
        url = myUrl + "results.json";
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return view;

    }
    private void setInitialData(String name, String role, String country, String img, String score) {

        states.add(new ResultState(name,role,country,img, score));
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
                                        json.optString("date"),
                                        json.optString("img"),
                                        json.optString("score"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        ResultStateAdapter adapter = new ResultStateAdapter(view.getContext(), states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }






}
