package com.sport.info

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class LoadActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        val linear: LinearLayout = findViewById(R.id.linear_load)
        Glide.with(this@LoadActivity)
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
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this@LoadActivity, MainActivity::class.java)
        val handler = Handler()
        handler.postDelayed({ startActivity(intent) }, 3000)
    }
}