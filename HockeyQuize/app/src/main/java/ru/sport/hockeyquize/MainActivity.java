package ru.sport.hockeyquize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    QuizeViewModel viewModel;
    List<QuestionData> questions;
    TextView tv_question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout background = findViewById(R.id.main_back);
        String imageUrl = "http://159.69.90.204/api/HockeyQuize/background.png"; // Замените ссылку на вашу URL-ссылку на изображение
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                background.setBackground(new BitmapDrawable(getResources(), bitmap));
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
        tv_question = findViewById(R.id.textView);
        viewModel = new ViewModelProvider(this).get(QuizeViewModel.class);
        viewModel.getQuiestions().observe(this, new Observer<List<QuestionData>>() {
            @Override
            public void onChanged(List<QuestionData> questionData) {
                setUpNextQuestion();
            }
        });
    }

    private void setUpNextQuestion(){
        tv_question.setText(viewModel.getNextQuestion());
    }

    public void checkAnswer(View view) {
        switch (view.getId()) {
            case R.id.btn_true:
                if (viewModel.getAnswer()) {
                    setUpNextQuestion();
                }else {
                    Toast.makeText(this,"Its false!",Toast.LENGTH_LONG).show();
                }
                break;
            case  R.id.btn_false:
                if (!viewModel.getAnswer()) {
                    setUpNextQuestion();
                }else {
                    Toast.makeText(this,"Its true!",Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
}