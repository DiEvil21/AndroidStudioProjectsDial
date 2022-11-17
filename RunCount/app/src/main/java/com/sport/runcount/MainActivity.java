package com.sport.runcount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    LinearLayout linear;
    SensorManager sensorManager;
    TextView tv_steps, tv_meters, tv_kal;
    String startTime, endTime;
    int height, weight;
    boolean running;
    ArrayList<String> arr = new ArrayList<String>();

    private Button clearCounterButton;
    private Integer stepCount = 0;
    private double MagnitudePrevious = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear = (LinearLayout) findViewById(R.id.linear_main);
        tv_steps = findViewById(R.id.tv_steps);
        tv_meters = findViewById(R.id.tv_meteres);
        tv_kal = findViewById(R.id.tv_kkal);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
                }
            }
        }
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/RunCount/background.jpg")
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linear.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Удалить данные")
                        .setMessage("Вы действительно хотите удалить все данные?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                prefs.edit().clear().commit();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case R.id.btn_progress:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Очистить прогресс")
                        .setMessage("Вы действительно хотите удалить данные о шагах, метрах и калориях?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                stepCount = 0;
                                tv_kal.setText("Калорий: - 0");
                                tv_steps.setText("0\nшагов");
                                tv_meters.setText("Метров: ");
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        height = Integer.parseInt(prefs.getString("height", null));
        weight = Integer.parseInt(prefs.getString("weight", null));
        if (prefs.getString("steps", null) != null) {
            stepCount = Integer.parseInt((prefs.getString("steps", null)));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this,countSensor, SensorManager.SENSOR_DELAY_UI);
        }else {
            Toast.makeText(this, "Датчик не найден",Toast.LENGTH_SHORT).show();

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] > 6){
            stepCount++;
        }
        tv_steps.setText(stepCount.toString() + "\nшагов");
        //(Рост / 4) + 0,37
        int mt = (int) (stepCount * ((height/400) +0.37));
        tv_meters.setText("Метров: " +mt);
        // 1.15 * m *  stepCount * (height/400) +0.37)  / 100000
        int k = (int) (1.15 * weight * stepCount * ((height/400) +0.37) / 100000);
        tv_kal.setText( "килокалорий: - " + k);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("steps", String.valueOf(stepCount));
        editor.apply();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}