package com.sport.truefalseapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import okhttp3.*
import org.json.JSONArray
import org.json.JSONTokener
import java.io.IOException


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private var client = OkHttpClient()
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var linear : LinearLayout

    private val mViewModel: myViewModel by lazy {
        ViewModelProvider(this).get(myViewModel::class.java)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called");
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        linear = findViewById(R.id.linear_main)
        questionTextView = findViewById(R.id.question_text_view)
        Glide.with(this)
            .load("http://159.69.90.204/api/truefalseApp/img/background.jpg")
            .into(object : CustomTarget<Drawable?>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    linear.setBackground(resource)
                }
            })
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        run()


    }




    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, mViewModel.currentIndex)
    }

    private fun updateQuestion() {

        val questionTextResId = mViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = mViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        val builder = AlertDialog.Builder(this)
            .setTitle(messageResId)
            .setMessage(mViewModel.currectQuestionAbout)
            .setPositiveButton("ОК, дальше") {
                    dialog, id ->  dialog.cancel()
                    mViewModel.moveToNext()
                    updateQuestion()
            }
        builder.create().show()

    }

    fun run() {
        val request = Request.Builder()
            .url("http://159.69.90.204/api/truefalseApp/questions.json")
            .build()
        client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    if (response.body != null) {
                        val jsonArray = JSONTokener(response.body?.string()).nextValue() as JSONArray
                        for (i in 0 until jsonArray.length()) {

                            val question = jsonArray.getJSONObject(i).getString("question")

                            var answer = jsonArray.getJSONObject(i).getString("answer") == "true"

                            val about = jsonArray.getJSONObject(i).getString("about")

                            mViewModel.addNewQ(question,answer, about)



                        }
                        runOnUiThread(Runnable {
                            updateQuestion()
                        })
                    }
                }
            }
        })
    }
}