package com.sport.sporttests.finish_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;

import com.sport.sporttests.MainActivity;
import com.sport.sporttests.R;

public class FinishActivity extends AppCompatActivity {
    TextView score, about;
    String scoreStr, q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        Intent intent = getIntent();
        scoreStr = intent.getStringExtra("score");
        q = intent.getStringExtra("Qcount");
        score = findViewById(R.id.textView8);
        about = findViewById(R.id.textView9);
    }

    @Override
    protected void onStart() {
        super.onStart();

        score.setText("Правильных ответов: " + scoreStr +" из " + q);
        if (scoreStr == q) {
            about.setText("Отлично все ответы верны. Ты молодец!");
        }
        if ( Integer. valueOf(q) /2 >  Integer. valueOf(scoreStr)) {
            about.setText("Повезет в другой раз");
        }
        if (Integer. valueOf(q) /2 <=  Integer. valueOf(scoreStr)) {
            about.setText("Неплохо, но можно лучше");
        }
    }
    public void onCLick(View v) {
        Intent intent = new Intent(FinishActivity.this, MainActivity.class);
        startActivity(intent);
    }
}