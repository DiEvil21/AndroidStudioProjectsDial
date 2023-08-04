package com.sport.ktprila

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.RelativeLayout

class LoadActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        val imageUrl = "http://159.69.90.204/api/Ktprila/background.jpg"
        val linearLayout = findViewById<RelativeLayout>(R.id.load_back)
        DownloadImageTask(linearLayout).execute(imageUrl)
        Handler().postDelayed({
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}