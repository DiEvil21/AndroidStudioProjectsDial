package ru.sport.aplligaapp.fragments.wall;

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


public class WallFragment extends Fragment {
    ArrayList<WallState> states = new ArrayList<WallState>();
    View view;
    ProgressBar bar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wall, container, false);
        bar = view.findViewById(R.id.progress_bar);
        try {
            sendNudes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }



    public void setData(String title, String date, String text, String image){

        states.add(new WallState (title,date,text,image));


    }


    void sendNudes() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://159.69.90.204/api/APLLigaApp/news.json")
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
                            states = new ArrayList<WallState>();
                            JSONArray arr = new JSONArray(myResponse);
                            JSONObject jsonObject = arr.getJSONObject(1);
                            System.out.println("---------------------------------------------------");
                            System.out.println(arr.getJSONObject(0));
                            for (int i = 0 ; i < arr.length() ; i++) {
                                jsonObject = new JSONObject(arr.get(i).toString());
                                System.out.println(jsonObject.optString("tittle"));
                                setData(jsonObject.optString("tittle"),
                                        jsonObject.optString("date"),
                                        jsonObject.optString("text"),
                                        jsonObject.optString("img"));



                            }
                            RecyclerView recyclerView = view.findViewById(R.id.list);
                            // создаем адаптер
                            WallStateAdapter adapter = new WallStateAdapter(view.getContext(), states);
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