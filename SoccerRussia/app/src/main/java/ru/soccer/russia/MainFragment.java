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

public class MainFragment extends Fragment {
    SharedPreferences mSettings;
    ArrayList<MainState> states = new ArrayList<MainState>();
    View view;
    String url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String myUrl = mSettings.getString(APP_PREFERENCES_URL,"");
        url = myUrl + "main.json";
        view = inflater.inflate(R.layout.fragment_main, container, false);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void setInitialData(String name,String text,String textRight,String img) {

        states.add(new MainState(name,text,textRight, img));
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
                            JSONArray arr = new JSONArray(myResponse);

                            for (int i = 0 ; arr.length() > i ; i++) {
                                JSONObject jsonObject = new JSONObject(String.valueOf(arr.get(i)));
                                System.out.println(jsonObject.optString("name"));
                                JSONArray array = new JSONArray(jsonObject.optString("right"));
                                JSONArray arrayleft = new JSONArray(jsonObject.optString("left"));
                                String str = "", strR = "";

                                for (int f = 0 ; array.length() > f ; f++) {
                                    str = str + array.get(f) + " \n";
                                }

                                for (int g = 0 ; arrayleft.length() > g ; g++) {
                                    strR = strR+ arrayleft.get(g).toString() + ": \n";
                                }
                                System.out.println(jsonObject);
                                setInitialData(jsonObject.optString("name"), str,strR, jsonObject.optString("img"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        MainStateAdapter adapter = new MainStateAdapter(view.getContext(), states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }
}
