package com.sport.ktsportapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class LoadActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        val imageUrl = "http://159.69.90.204/api/KtSportApp/background.jpg"
        val linearLayout = findViewById<RelativeLayout>(R.id.load_back)
        MainActivity.DownloadImageTask(linearLayout).execute(imageUrl)
        // Ожидаем 3 секунды с помощью Handler и затем открываем MainActivity
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Закрываем SplashActivity, чтобы пользователь не мог вернуться назад на экран приветствия
        }, 3000)
    }

    private class DownloadImageTask(linearLayout: RelativeLayout) : AsyncTask<String, Void, Bitmap?>() {

        private val linearLayoutReference: WeakReference<RelativeLayout> = WeakReference(linearLayout)

        override fun doInBackground(vararg urls: String): Bitmap? {
            try {
                val imageUrl = urls[0]
                return Picasso.get().load(imageUrl).get()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            val linearLayout = linearLayoutReference.get()
            if (linearLayout != null && result != null) {
                // Установка изображения в качестве фона для LinearLayout
                val drawable = BitmapDrawable(linearLayout.resources, result)
                linearLayout.background = drawable
            }
        }

    }

}