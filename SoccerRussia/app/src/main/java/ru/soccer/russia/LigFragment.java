package ru.soccer.russia;

import static ru.soccer.russia.ChooseActivity.APP_PREFERENCES_NAME;
import static ru.soccer.russia.ChooseActivity.APP_PREFERENCES_URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LigFragment extends Fragment {
    SharedPreferences mSettings;
    ArrayList<LigState1> states = new ArrayList<LigState1>();
    ArrayList<LigState2> states2 = new ArrayList<LigState2>();
    String url, url2;
    View view;
    TextView st1,st2,st3,st4,st5,st6,st7,st8,st0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lig, container, false);
        //-------------------------------------------------
        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String myUrl = mSettings.getString(APP_PREFERENCES_URL,"");
        url = myUrl + "previosmatchs.json";
        url2 = myUrl + "ligchampions.json";
        //----------------------------------

        //---------------------------------
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
        try {
            run2();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setInitialData(String textLeft, String imgLeft, String imgRight, String textRight) {

        states.add(new LigState1(textLeft,textRight,imgLeft,imgRight));
    }
    private void setInitialData2(String st1,String st2,String st3,String st4,String st5,String st6,String st7,String st8,String sti, String count) {

        states2.add(new LigState2(st1,st2,st3,st4,st5,st6,st7,st8,sti,count));
    }

    void run2() throws IOException {
        System.out.println("---------------------------------");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url2)
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
                            st0.setText("№");
                            st1.setText(jsonObject.optString("st1"));
                            st2.setText(jsonObject.optString("st2"));
                            st3.setText(jsonObject.optString("st3"));
                            st4.setText(jsonObject.optString("st4"));
                            st5.setText(jsonObject.optString("st5"));
                            st6.setText(jsonObject.optString("st6"));
                            st7.setText(jsonObject.optString("st7"));
                            st8.setText(jsonObject.optString("st8"));
                            System.out.println(jsonObject.getJSONArray("teams").length());
                            for (int i = 0 ; jsonObject.getJSONArray("teams").length() > i; i++){
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("teams").get(i)));
                                setInitialData2(json.optString("st1"),
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

                        RecyclerView recyclerView = view.findViewById(R.id.list22);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        Lig2StateAdapter adapter2 = new Lig2StateAdapter(view.getContext(), states2);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter2);





                    }

                });

            }
        });
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
                final String myResponse = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);

                            for (int i = 0 ; jsonObject.getJSONArray("players").length() > i; i++){
                                JSONObject json = new JSONObject(String.valueOf(jsonObject.getJSONArray("players").get(i)));
                                setInitialData(json.optString("textleft"),
                                        json.optString("imgleft"),
                                        json.optString("imgright"),
                                        json.optString("textright"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.list1);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        Lig1StateAdapter adapter = new Lig1StateAdapter(view.getContext(), states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);





                    }

                });

            }
        });
    }




}


