package com.sport.ktmvvmquize

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class StartActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        // Находим кнопку по id
        val startButton = findViewById<Button>(R.id.startButton)
        val imageUrl = "http://159.69.90.204/api/KTmvvmQuize/background.jpg"
        val linearLayout = findViewById<LinearLayout>(R.id.start_back)
        DownloadImageTask(linearLayout).execute(imageUrl)
        // Устанавливаем обработчик нажатия
        startButton.setOnClickListener {
            // Запускаем MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}