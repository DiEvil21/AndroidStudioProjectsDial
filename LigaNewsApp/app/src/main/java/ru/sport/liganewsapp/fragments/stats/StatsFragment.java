package ru.sport.liganewsapp.fragments.stats;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import ru.sport.liganewsapp.R;


public class StatsFragment extends Fragment {
    View view;
    ArrayList<StatsState> states = new ArrayList<StatsState>();
    ArrayList<StatsState2> states2 = new ArrayList<StatsState2>();
    ArrayList<StatsState3> states3 = new ArrayList<StatsState3>();
    LinearLayout linear1, linear2, linear3;
    NestedScrollView scrollView;
    ProgressBar mProgressBar;
    TextView tv_header1, tv_header2, tv_header3;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        tv_header1 = view.findViewById(R.id.tv_list1_header);
        tv_header2 = view.findViewById(R.id.tv_list2_header);
        tv_header3 = view.findViewById(R.id.tv_list3_header);
        linear1 = view.findViewById(R.id.linear1);
        linear2 = view.findViewById(R.id.linear2);
        linear3 = view.findViewById(R.id.linear3);
        scrollView = view.findViewById(R.id.scroll);
        scrollView.setVisibility(View.INVISIBLE);;

        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                linear1.setBackground(getResources().getDrawable(R.drawable.background_text));
                linear2.setBackground(getResources().getDrawable(R.drawable.background_text));
                linear3.setBackground(getResources().getDrawable(R.drawable.background_text));
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                linear1.setBackground(getResources().getDrawable(R.drawable.background_news));
                linear2.setBackground(getResources().getDrawable(R.drawable.background_news));
                linear3.setBackground(getResources().getDrawable(R.drawable.background_news));
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                linear1.setBackground(getResources().getDrawable(R.drawable.background_text));
                linear2.setBackground(getResources().getDrawable(R.drawable.background_text));
                linear3.setBackground(getResources().getDrawable(R.drawable.background_text));
                break;
        }


        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mProgressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    private void setData(String date, String owner, String score, String quest) {

        states.add(new StatsState(date,owner,score,quest));
    }
    private void setData2(String date, String owner, String score, String quest) {

        states2.add(new StatsState2(date,owner,score,quest));
    }
    private void setData3(String date, String owner, String score, String quest) {

        states3.add(new StatsState3(date,owner,score,quest));
    }



    void run() throws IOException {
        System.out.println("---------------------------------");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/LigaNewsApp/stats.json")
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
                            //System.out.println(myResponse);
                            JSONArray arr = new JSONArray(myResponse);
                            //System.out.println(arr.get(0).toString());
                            JSONObject jsonObject1 = new JSONObject(arr.getJSONObject(0).toString());
                            System.out.println(jsonObject1);
                            JSONArray array = new JSONArray(jsonObject1.optString("stats"));
                            tv_header1.setText(jsonObject1.optString("name"));
                            for (int i = 0 ; i < array.length(); i++) {
                                JSONObject jsonObj  =  array.getJSONObject(i);
                                System.out.println(jsonObj.optString("date"));
                                setData(jsonObj.optString("date"),
                                        jsonObj.optString("owner"),
                                        jsonObj.optString("score"),
                                        jsonObj.optString("quests"));
                            }
                            //------------------------------------------------------------
                            JSONObject jsonObject2 = new JSONObject(arr.getJSONObject(1).toString());
                            JSONArray array2 = new JSONArray(jsonObject2.optString("stats"));
                            tv_header2.setText(jsonObject2.optString("name"));
                            for (int i = 0 ; i < array2.length(); i++) {
                                JSONObject jsonObj  =  array2.getJSONObject(i);
                                System.out.println(jsonObj.optString("date"));
                                setData2(jsonObj.optString("date"),
                                        jsonObj.optString("owner"),
                                        jsonObj.optString("score"),
                                        jsonObj.optString("quests"));
                            }
                            //-------------------------------------------------------------
                            JSONObject jsonObject3 = new JSONObject(arr.getJSONObject(2).toString());
                            JSONArray array3 = new JSONArray(jsonObject3.optString("stats"));
                            tv_header3.setText(jsonObject3.optString("name"));
                            for (int i = 0 ; i < array3.length(); i++) {
                                JSONObject jsonObj  =  array3.getJSONObject(i);
                                System.out.println(jsonObj.optString("date"));
                                setData3(jsonObj.optString("date"),
                                        jsonObj.optString("owner"),
                                        jsonObj.optString("score"),
                                        jsonObj.optString("quests"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.list);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        StatsStateAdapter adapter = new StatsStateAdapter(view.getContext(), states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);
                        //-------------------------------------------------------------------
                        RecyclerView recyclerView2 = view.findViewById(R.id.list2);
                        recyclerView2.setNestedScrollingEnabled(false);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        StatsStateAdapter2 adapter2 = new StatsStateAdapter2(view.getContext(), states2);
                        // устанавливаем для списка адаптер
                        recyclerView2.setAdapter(adapter2);
                        //--------------------------------------------------------------------
                        RecyclerView recyclerView3 = view.findViewById(R.id.list3);
                        recyclerView2.setNestedScrollingEnabled(false);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        StatsStateAdapter3 adapter3 = new StatsStateAdapter3(view.getContext(), states3);
                        // устанавливаем для списка адаптер
                        recyclerView3.setAdapter(adapter3);
                        mProgressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);


                    }

                });

            }
        });
    }


}