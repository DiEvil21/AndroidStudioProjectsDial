package com.sport.ktprila

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: QuizViewModel
    private lateinit var questionTextView: TextView
    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var submitAnswerButton: Button
    private var currentQuestionIndex = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.questionTextView)
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup)
        submitAnswerButton = findViewById(R.id.submitAnswerButton)
        val imageUrl = "http://159.69.90.204/api/Ktprila/background.jpg"
        val linearLayout = findViewById<RelativeLayout>(R.id.main_back)
        DownloadImageTask(linearLayout).execute(imageUrl)
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        viewModel.quizQuestions.observe(this, Observer { questions ->
            if (questions.isNotEmpty()) {
                showQuestion(questions[currentQuestionIndex])
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loadQuizQuestions()

        submitAnswerButton.setOnClickListener {
            checkAnswer()
        }
    }

    private fun showQuestion(question: Question) {
        questionTextView.text = question.text
        optionsRadioGroup.removeAllViews()

        question.options.forEachIndexed { index, optionText ->
            val radioButton = RadioButton(this)
            radioButton.id = index
            radioButton.text = optionText
            optionsRadioGroup.addView(radioButton)
        }

        submitAnswerButton.isEnabled = true
    }

    private fun checkAnswer() {
        val selectedOptionId = optionsRadioGroup.checkedRadioButtonId
        if (selectedOptionId == -1) {
            Toast.makeText(this, "Please, select the answer", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedOptionIndex = optionsRadioGroup.indexOfChild(findViewById<RadioButton>(selectedOptionId))
        val currentQuestion = viewModel.quizQuestions.value?.get(currentQuestionIndex)

        if (currentQuestion != null && selectedOptionIndex == currentQuestion.correctOptionIndex) {
            Toast.makeText(this, "True", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "False", Toast.LENGTH_SHORT).show()
        }

        if (currentQuestionIndex < viewModel.quizQuestions.value?.size?.minus(1) ?: 0) {
            currentQuestionIndex++
            showQuestion(viewModel.quizQuestions.value?.get(currentQuestionIndex) ?: return)
        } else {
            Toast.makeText(this, "The end", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)

        }
    }
}