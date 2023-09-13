package ru.sport.boxquizeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private QuizViewModel quizViewModel;
    private TextView questionTextView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private int currentQuestionIndex = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        LinearLayout background = findViewById(R.id.quize_background);
        String imageUrl = "http://159.69.90.204/api/BoxQuizeApp/background.jpg";
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                background.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        // Инициализация элементов интерфейса
        questionTextView = findViewById(R.id.question_text);
        option1Button = findViewById(R.id.option1_button);
        option2Button = findViewById(R.id.option2_button);
        option3Button = findViewById(R.id.option3_button);
        option4Button = findViewById(R.id.option4_button);

        // Инициализация ViewModel
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // Наблюдение за LiveData с вопросами
        quizViewModel.getQuestionsLiveData().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                if (questions != null && !questions.isEmpty()) {
                    displayQuestion(questions.get(currentQuestionIndex));
                }
            }
        });

        // Обработчики нажатия кнопок с вариантами ответов
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(0);
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(1);
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(2);
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(3);
            }
        });
    }

    private void displayQuestion(Question question) {
        questionTextView.setText(question.getQuestionText());
        List<String> options = question.getOptions();
        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));
        option4Button.setText(options.get(3));
    }

    private void checkAnswer(int selectedOptionIndex) {
        List<Question> questions = quizViewModel.getQuestionsLiveData().getValue();
        if (questions != null && !questions.isEmpty()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            if (selectedOptionIndex == currentQuestion.getCorrectOptionIndex()) {
                // Ответ правильный, перейдите к следующему вопросу
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion(questions.get(currentQuestionIndex));
                } else {
                    // Все вопросы пройдены
                    Toast.makeText(this, "Вы завершили викторину!", Toast.LENGTH_SHORT).show();
                    // Здесь вы можете добавить логику для завершения активити или перехода к другой странице
                }
            } else {
                // Ответ неправильный, выведите тост
                Toast.makeText(this, "Неправильный ответ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

