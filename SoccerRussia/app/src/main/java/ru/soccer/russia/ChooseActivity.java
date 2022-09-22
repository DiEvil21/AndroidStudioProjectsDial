package ru.soccer.russia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ChooseActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES_NAME = "myname";
    public static final String APP_PREFERENCES_URL = "myurl";
    public static final String APP_PREFERENCES_COMMAND = "mycommand";
    public static final String APP_PREFERENCES_IMG = "commandimg";
    ImageView img1,img2,img3,img4,img5;
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        //---------------------------------------
        img1 = findViewById(R.id.imageView1);
        img2 = findViewById(R.id.imageView2);
        img3 = findViewById(R.id.imageView3);
        img4 = findViewById(R.id.imageView4);
        img5 = findViewById(R.id.imageView5);
        //----------------------------------------
        new DownloadImageTask(img1).execute("http://159.69.90.204/api/soccerstats/teams/teamslogo/barcelona.png");
        new DownloadImageTask(img2).execute("http://159.69.90.204/api/soccerstats/teams/teamslogo/bavaria.png");
        new DownloadImageTask(img3).execute("http://159.69.90.204/api/soccerstats/teams/teamslogo/inter.png");
        new DownloadImageTask(img4).execute("http://159.69.90.204/api/soccerstats/teams/teamslogo/viktoria_plzen.png");
        new DownloadImageTask(img5).execute("http://159.69.90.204/api/soccerstats/teams/teamslogo/viktoria_plzen.png");
        mSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = mSettings.edit();
    }

    public void onCLick(View v) {
        Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
        switch (v.getId()) {
            case R.id.buttonMenu1:
                editor.putString(APP_PREFERENCES_URL, "http://159.69.90.204/api/soccerstats/teams/barselona/");
                editor.putString((APP_PREFERENCES_COMMAND), "Барселона");
                editor.putString((APP_PREFERENCES_IMG), "http://159.69.90.204/api/soccerstats/teams/teamslogo/barcelona.png");
                break;
            case R.id.buttonMenu2:
                editor.putString(APP_PREFERENCES_URL, "http://159.69.90.204/api/soccerstats/teams/bavaria/");
                editor.putString((APP_PREFERENCES_COMMAND), "Бавария");
                editor.putString((APP_PREFERENCES_IMG), "http://159.69.90.204/api/soccerstats/teams/teamslogo/bavaria.png");
                break;
            case R.id.buttonMenu3:
                editor.putString(APP_PREFERENCES_URL, "http://159.69.90.204/api/soccerstats/teams/inter/");
                editor.putString((APP_PREFERENCES_COMMAND), "Интер");
                editor.putString((APP_PREFERENCES_IMG), "http://159.69.90.204/api/soccerstats/teams/teamslogo/inter.png");

                break;
            case R.id.buttonMenu4:
                editor.putString(APP_PREFERENCES_URL, "http://159.69.90.204/api/soccerstats/teams/viktoriaplzen/");
                editor.putString((APP_PREFERENCES_COMMAND), "Виктория Пльзень");
                editor.putString((APP_PREFERENCES_IMG), "http://159.69.90.204/api/soccerstats/teams/teamslogo/viktoria_plzen.png");

                break;
            case R.id.buttonMenu5:
                editor.putString(APP_PREFERENCES_URL, "http://159.69.90.204/api/soccerstats/teams/liverpool/");
                editor.putString((APP_PREFERENCES_COMMAND), "Ливерпуль");
                editor.putString((APP_PREFERENCES_IMG), "http://159.69.90.204/api/soccerstats/teams/teamslogo/liverpool.png");

                break;
        }
        editor.apply();

        startActivity(intent);
    }

}