package ru.sport.mvvmquize;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private QuizViewModel quizViewModel;
    private List<Question> questions;
    private int currentQuestionIndex;

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button nextButton;

    List<Integer> userAnswers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ImageView backgroundImageView = findViewById(R.id.backgroundImageView);
        Picasso.get().load("http://159.69.90.204/api/MVVMQuize/background.jpg").into(backgroundImageView);
        // Замените "http://example.com/background.jpg" на URL вашего фонового изображения

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        nextButton = findViewById(R.id.nextButton);

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        quizViewModel.getQuestionsLiveData().observe(this, questions -> {
            this.questions = questions;
            currentQuestionIndex = 0;
            showQuestion();
        });

        quizViewModel.loadQuestions();
    }

    private void showQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionTextView.setText(question.getQuestionText());

        optionsRadioGroup.removeAllViews();
        for (int i = 0; i < question.getOptions().size(); i++) {
            String option = question.getOptions().get(i);

            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            radioButton.setTag(i);

            optionsRadioGroup.addView(radioButton);
        }

        nextButton.setEnabled(false);
        optionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> nextButton.setEnabled(true));
    }

    public void onNextButtonClick(View view) {
        Question question = questions.get(currentQuestionIndex);
        int selectedOptionIndex = (int) optionsRadioGroup.findViewById(optionsRadioGroup.getCheckedRadioButtonId()).getTag();
        userAnswers.add(selectedOptionIndex);
        String message;
        if (selectedOptionIndex == question.getCorrectOptionIndex()) {
            message = "True! \n";
        } else {
            String correctOption = question.getOptions().get(question.getCorrectOptionIndex());
            message = "False! \n" + correctOption;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Next", (dialog, which) -> {
                    dialog.dismiss();
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        showQuestion();
                    } else {
                        // Викторина завершена
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        String questionsJson = new Gson().toJson(questions);
                        intent.putExtra("questionsJson", questionsJson);
                        intent.putIntegerArrayListExtra("userAnswers", (ArrayList<Integer>) userAnswers);
                        startActivity(intent);

                    }
                });
        builder.create().show();
    }
}
