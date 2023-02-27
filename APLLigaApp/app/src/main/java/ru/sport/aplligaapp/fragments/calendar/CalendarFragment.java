package ru.sport.aplligaapp.fragments.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ru.sport.aplligaapp.R;


public class CalendarFragment extends Fragment {
    View view;
    ProgressBar bar;
    ArrayList<CalendarState> states = new ArrayList<CalendarState>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        bar = view.findViewById(R.id.progress_bar);
        try {
            sendNudes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }


    private void setData(String date, String owner, String score, String quest) {

        states.add(new CalendarState(date,owner,score,quest));
    }


    void sendNudes() throws IOException {
        System.out.println("---------------------------------");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/APLLigaApp/calendar.json")
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
                            for (int i = 0 ; i < array.length(); i++) {
                                JSONObject jsonObj  =  array.getJSONObject(i);
                                System.out.println(jsonObj.optString("date"));
                                setData(jsonObj.optString("date"),
                                        jsonObj.optString("owner"),
                                        jsonObj.optString("score"),
                                        jsonObj.optString("quests"));
                            }
                            bar.setVisibility(View.GONE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.list);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // создаем адаптер
                        CalendarStateAdapter adapter = new CalendarStateAdapter(view.getContext(), states);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);
                        //-------------------------------------------------------------------




                    }

                });

            }
        });
    }

}