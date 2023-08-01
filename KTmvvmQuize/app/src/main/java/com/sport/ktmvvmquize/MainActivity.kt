package com.sport.ktmvvmquize

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: QuizViewModel
    private var currentQuestion: Question? = null
    private lateinit var answerRadioGroup: RadioGroup

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageUrl = "http://159.69.90.204/api/KTmvvmQuize/background.jpg"
        val linearLayout = findViewById<LinearLayout>(R.id.main_back)
        DownloadImageTask(linearLayout).execute(imageUrl)
        answerRadioGroup = findViewById(R.id.answerRadioGroup)
        val api = Retrofit.Builder()
            .baseUrl("http://159.69.90.204/api/KTmvvmQuize/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HockeyQuizApi::class.java)

        viewModel = ViewModelProvider(this, QuizViewModelFactory(api)).get(QuizViewModel::class.java)

        // Обновляем пользовательский интерфейс с полученными вопросами
        viewModel.questions.observe(this) { questions ->
            if (questions.isNotEmpty()) {
                currentQuestion = questions[0]
                updateQuestionUI(currentQuestion)
            }
        }
        viewModel.currentQuestion.observe(this) { currentQuestion ->
            updateQuestionUI(currentQuestion)
        }

        viewModel.fetchQuestions()

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            val selectedAnswer = findViewById<RadioButton>(answerRadioGroup.checkedRadioButtonId)?.text.toString()
            checkAnswer(selectedAnswer)
        }
    }

    private fun updateQuestionUI(question: Question?) {
        question?.let {
            // Отобразить текст вопроса
            val questionTextView = findViewById<TextView>(R.id.questionTextView)
            questionTextView.text = question.questionText

            // Отобразить изображение вопроса (если есть)
            val questionImageView = findViewById<ImageView>(R.id.questionImageView)
            if (question.imageUrl != null) {
                Picasso.get().load(question.imageUrl).into(questionImageView)
                questionImageView.visibility = View.VISIBLE
            } else {
                questionImageView.visibility = View.GONE
            }

            // Сбросить выбор радио-кнопок
            val answerRadioGroup = findViewById<RadioGroup>(R.id.answerRadioGroup)
            answerRadioGroup.clearCheck()
        }
    }

    private fun checkAnswer(selectedAnswer: String) {
        currentQuestion?.let {
            val isAnswerCorrect = if (selectedAnswer == "True") it.correctAnswer else !it.correctAnswer

            Toast.makeText(this, if (isAnswerCorrect) "Correct!" else "Incorrect!", Toast.LENGTH_SHORT).show()

            if (viewModel.isLastQuestion()) {
                endGame()
            } else {
                // Перейти к следующему вопросу
                viewModel.moveToNextQuestion()
            }
        }
    }


    private fun endGame() {

        Toast.makeText(this, "End of Quiz!", Toast.LENGTH_LONG).show()
    }

}

