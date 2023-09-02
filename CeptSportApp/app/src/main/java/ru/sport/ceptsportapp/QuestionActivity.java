package ru.sport.ceptsportapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private QuestionViewModel questionViewModel;
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button nextButton;
    private List<Question> questionss;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout background = findViewById(R.id.question_back);
        String imageUrl = "http://159.69.90.204/api/CeptSportApp/background.jpg";
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
        questionTextView = findViewById(R.id.question_text_view);
        optionsRadioGroup = findViewById(R.id.options_radio_group);
        nextButton = findViewById(R.id.next_button);

        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        questionViewModel.getQuestionsLiveData().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                if (questions != null && questions.size() > 0) {
                    // Получение первого вопроса из списка
                    Question currentQuestion = questions.get(0);
                    questionss = questions;

                    // Обновление интерфейса с вопросом и вариантами ответов
                    updateQuestionUI(currentQuestion);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Обработка нажатия кнопки "Далее"
                // Получение следующего вопроса и обновление интерфейса
                checkAnswerAndShowNextQuestion();

            }
        });
    }
    private void checkAnswerAndShowNextQuestion() {
        if (questionss != null && currentQuestionIndex < questionss.size() - 1) {
            // Получение текущего вопроса
            Question currentQuestion = questionss.get(currentQuestionIndex);

            // Получение выбранного ответа (индекс выбранной радиокнопки)
            int selectedAnswerIndex = optionsRadioGroup.getCheckedRadioButtonId();

            // Проверка правильности ответа
            if (selectedAnswerIndex == currentQuestion.getCorrectOptionIndex()) {
                // Правильный ответ, переход к следующему вопросу
                currentQuestionIndex++;
                Question nextQuestion = questionss.get(currentQuestionIndex);
                updateQuestionUI(nextQuestion);
            } else {
                // Неправильный ответ, показываем тост
                showToast("Incorrect Answer! Try Again.");
            }
        } else {
            showToast("The end!");
            Intent intent = new Intent(QuestionActivity.this,StartActivity.class);
            startActivity(intent);
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestionUI(Question question) {
        questionTextView.setText(question.getQuestionText());

        optionsRadioGroup.removeAllViews(); // Очистка радиогруппы

        // Создание радиокнопок для вариантов ответов
        for (int i = 0; i < question.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(question.getOptions().get(i));
            radioButton.setId(i);
            // Установка цвета текста
            radioButton.setTextColor(ContextCompat.getColor(this, R.color.white)); // Замените "white" на ваш цвет
            // Установка цвета круглых точек (чек-боксов)
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)); // Замените "white" на ваш цвет
            radioButton.setButtonTintList(colorStateList);
            optionsRadioGroup.addView(radioButton);
        }
    }
}

