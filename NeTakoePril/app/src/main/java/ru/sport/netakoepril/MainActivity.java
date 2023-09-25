package ru.sport.netakoepril;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private float currentAngle = 0.0f;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_rotate = findViewById(R.id.btn_rotate);

        btn_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateWheelToRandomAngle();
            }
        });
        Button btn_more = findViewById(R.id.btn_more);

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MoreActivity.class);
                startActivity(intent);
            }
        });
        TextView tv_about = findViewById(R.id.tv_about);
        fetchAboutData(this,tv_about);
    }



    private void rotateWheelToRandomAngle() {
        ImageView rouletteWheel = findViewById(R.id.iv_roulette);

        Random random = new Random();
        int randomAngle = 1000+ random.nextInt(2000);

        RotateAnimation rotateAnimation = new RotateAnimation(
                currentAngle, currentAngle + randomAngle,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        currentAngle += randomAngle;
        rouletteWheel.startAnimation(rotateAnimation);
    }

    public static void fetchAboutData(Context context, final TextView textView) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "http://159.69.90.204/api/NeTakoePril/roulette_about.json";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String aboutText = response.getString("about");

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