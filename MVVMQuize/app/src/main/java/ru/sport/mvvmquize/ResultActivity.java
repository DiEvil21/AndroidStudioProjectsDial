package ru.sport.mvvmquize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ru.sport.mvvmquize.Question;

import java.util.List;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private List<Question> questions;
    private List<Integer> userAnswers;

    private TextView percentageTextView;
    private RecyclerView resultRecyclerView;
    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout backgroundLinearLayout = findViewById(R.id.linear_result);
        String imageUrl = "http://159.69.90.204/api/MVVMQuize/background.jpg"; // Замените ссылку на вашу URL-ссылку на изображение
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                backgroundLinearLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                // Обработка ошибки загрузки изображения
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // Действия при подготовке загрузки изображения
            }
        });




        percentageTextView = findViewById(R.id.percentageTextView);
        resultRecyclerView = findViewById(R.id.resultRecyclerView);

        String questionsJson = getIntent().getStringExtra("questionsJson");
        questions = new Gson().fromJson(questionsJson, new TypeToken<List<Question>>(){}.getType());

        userAnswers = getIntent().getIntegerArrayListExtra("userAnswers");

        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int userAnswer = userAnswers.get(i);
            if (userAnswer == question.getCorrectOptionIndex()) {
                correctAnswers++;
            }
        }

        double percentage = (double) correctAnswers / questions.size() * 100;
        String percentageString = String.format(Locale.getDefault(), "%.2f%%", percentage);
        percentageTextView.setText(getString(R.string.percentage, percentageString));

        resultAdapter = new ResultAdapter(questions, userAnswers);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(resultAdapter);
    }
}
