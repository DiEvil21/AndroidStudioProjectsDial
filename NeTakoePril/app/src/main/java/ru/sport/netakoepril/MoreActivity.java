package ru.sport.netakoepril;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        TextView tv_more = findViewById(R.id.tv_more_text);
        fetchAboutData(this,tv_more);
    }





    public static void fetchAboutData(Context context, final TextView textView) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "http://159.69.90.204/api/NeTakoePril/roulette_more.json";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String aboutText = response.getString("more");

                            textView.setText(aboutText);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Обработка ошибки
                    }
                }
        );


        requestQueue.add(jsonObjectRequest);
    }
}