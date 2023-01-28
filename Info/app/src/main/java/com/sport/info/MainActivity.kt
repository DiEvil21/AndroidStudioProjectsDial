package com.sport.info

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linear: LinearLayout = findViewById(R.id.linear_main)
        Glide.with(this@MainActivity)
            .load("http://159.69.90.204/api/InfoApp/img/background.png")
            .into(object : CustomTarget<Drawable?>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    linear.setBackground(resource)
                }
            })
        val btn_start: Button = findViewById(R.id.btn_start)
        val btn_bets: Button  = findViewById(R.id.btn_bets)
        val btn_forecast: Button = findViewById(R.id.btn_forecast)

        btn_bets.setOnClickListener{

            val intent = Intent(this, BetsActivity::class.java)
            startActivity(intent)
        }

        btn_start.setOnClickListener{

            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }
        btn_forecast.setOnClickListener{
            val intent  = Intent(this, ForecastActivity::class.java)
            startActivity(intent)
        }
    }
}