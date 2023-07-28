package com.sport.ktsportapp

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sport.ktsportapp.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val imageUrl = "http://159.69.90.204/api/KtSportApp/background.jpg"
        val linearLayout = findViewById<RelativeLayout>(R.id.linearLayout)
        DownloadImageTask(linearLayout).execute(imageUrl)
        // По умолчанию отображаем первый фрагмент (NewsFragment)
        if (savedInstanceState == null) {
            showFragment(NewsFragment())
        }

        // Устанавливаем слушатель для Bottom Navigation
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_news -> showFragment(NewsFragment())
                R.id.action_hockey_stats -> showFragment(HockeyStatsFragment())
            }
            true
        }
    }
    class DownloadImageTask(linearLayout: RelativeLayout) : AsyncTask<String, Void, Bitmap?>() {

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

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .commit()
    }
}
