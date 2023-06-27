package ru.sport.clubquize;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private QuizViewModel viewModel;
    private ProgressBar progressBar;
    private ImageView clubImageView;
    private List<Button> optionButtons;
    private String currentClubName;
    private Observer<List<Club>> clubsObserver;
    private Handler handler;
    private Runnable runnable;
    private static final long TIMER_DURATION = 10000; // 10 seconds
    private static final long TIMER_INTERVAL = 1000; // 1 second
    private static final int PROGRESS_INCREMENT = 10;
    private boolean dialogShown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RelativeLayout backgroundLinearLayout = findViewById(R.id.quize_back);
        String imageUrl = "http://159.69.90.204/api/LogoQuize/logo/background.png"; // Замените ссылку на вашу URL-ссылку на изображение
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
        progressBar = findViewById(R.id.progressBar);
        clubImageView = findViewById(R.id.clubImageView);
        optionButtons = new ArrayList<>();
        optionButtons.add(findViewById(R.id.optionButton1));
        optionButtons.add(findViewById(R.id.optionButton2));
        optionButtons.add(findViewById(R.id.optionButton3));
        optionButtons.add(findViewById(R.id.optionButton4));

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        progressBar.setMax(10);
        progressBar.setProgress(10);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                decrementProgressBar();
                handler.postDelayed(this, TIMER_INTERVAL);
            }
        };
        // Наблюдение за изменениями списка клубов
        clubsObserver = new Observer<List<Club>>() {
            @Override
            public void onChanged(List<Club> clubs) {
                if (clubs != null && !clubs.isEmpty()) {
                    // Список клубов загружен, можно загружать первый вопрос
                    loadNextQuestion();
                }
            }
        };

        viewModel.getClubsLiveData().observe(this, clubsObserver);

        viewModel.loadClubs(); // Загружаем список клубов из apiService

        loadNextQuestion(); // Загружаем первый вопрос

        progressBar = findViewById(R.id.progressBar);




    }
    private void decrementProgressBar() {
        if (progressBar.getProgress() > 0 ) {
            if (clubImageView.getDrawable() != null) {
                progressBar.setProgress(progressBar.getProgress() - 1);
            }

        } else {
            if (!dialogShown) {
                dialogShown = true;
                // ProgressBar достиг минимального значения, обработайте это соответствующим образом
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("GameOver")
                        .setMessage("Time is over")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Действие при нажатии на кнопку "Заново"
                                recreate();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Действие при нажатии на кнопку "Выйти"
                                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelable(false) // Запретить закрытие диалогового окна при нажатии вне его области
                        .show();
            }

        }
    }


    public void onClick(View v) {
        if (v instanceof Button) {
            Button button = (Button) v;
            String buttonText = button.getText().toString().toLowerCase();

            if (buttonText.equals(currentClubName.toLowerCase())) {
                // Вы правильно ответили на текущий вопрос
                loadNextQuestion(); // Загружаем следующий вопрос
                progressBar.setProgress(progressBar.getProgress() + PROGRESS_INCREMENT);
            }
        }
    }
    private void updateProgressBar() {
        if (progressBar.getProgress() < progressBar.getMax()) {
            progressBar.setProgress(progressBar.getProgress() + PROGRESS_INCREMENT);
        } else {
            // ProgressBar достиг максимального значения, обработайте это соответствующим образом
        }
    }

    private void loadNextQuestion() {
        progressBar.setMax(10);
        progressBar.setProgress(10);
        handler.postDelayed(runnable, TIMER_INTERVAL);

        Club nextQuestion = viewModel.getNextQuestion();
        if (nextQuestion != null) {
            currentClubName = nextQuestion.getName();
            Picasso.get().load(nextQuestion.getImageUrl()).into(clubImageView);
            System.out.println(nextQuestion.getName());
            // Назначаем текст на кнопки
            List<String> randomOptions = getRandomOptions(nextQuestion.getName(), viewModel.getClubsLiveData().getValue());
            for (int i = 0; i < optionButtons.size(); i++) {
                optionButtons.get(i).setText(randomOptions.get(i));
            }
        } else {
            // Все вопросы исчерпаны
            // Обработайте это соответствующим образом
        }
    }

    private List<String> getRandomOptions(String correctOption, List<Club> allClubs) {
        List<String> options = new ArrayList<>();
        options.add(correctOption);

        // Получаем случайные неправильные варианты ответов
        while (options.size() < optionButtons.size()) {
            int randomIndex = new Random().nextInt(allClubs.size());
            String randomOption = allClubs.get(randomIndex).getName();
            if (!options.contains(randomOption) && !randomOption.equals(correctOption)) {
                options.add(randomOption);
            }
        }

        // Перемешиваем варианты ответов
        Collections.shuffle(options);

        return options;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Удаление наблюдателя при уничтожении активности
        viewModel.getClubsLiveData().removeObserver(clubsObserver);
    }




}


