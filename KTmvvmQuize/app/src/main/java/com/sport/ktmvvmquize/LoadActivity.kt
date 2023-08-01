package com.sport.ktmvvmquize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso

class LoadActivity : AppCompatActivity() {
    private val loadDelay: Long = 3000 // Задержка в 3 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        val imageUrl = "http://159.69.90.204/api/KTmvvmQuize/background.jpg"
        val linearLayout = findViewById<LinearLayout>(R.id.load_back)
        DownloadImageTask(linearLayout).execute(imageUrl)
        // Запускаем переключение на StartActivity через 3 секунды
        lifecycleScope.launchWhenCreated {
            delay(loadDelay)
            startActivity(Intent(this@LoadActivity, StartActivity::class.java))
            finish()
        }
    }
}
