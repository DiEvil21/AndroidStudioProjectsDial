package com.sport.ktprila

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout

class MenuActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val imageUrl = "http://159.69.90.204/api/Ktprila/background.jpg"
        val linearLayout = findViewById<RelativeLayout>(R.id.menu_back)
        DownloadImageTask(linearLayout).execute(imageUrl)
        val playButton: Button = findViewById(R.id.playButton)
        playButton.setOnClickListener {
            openMainActivity()
        }
    }
    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}