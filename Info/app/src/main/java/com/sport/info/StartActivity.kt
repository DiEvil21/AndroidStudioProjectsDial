package com.sport.info

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sport.info.bets.Adapter
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class StartActivity : AppCompatActivity() {
    private var client = OkHttpClient()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val linear: LinearLayout = findViewById(R.id.linear_start)
        Glide.with(this@StartActivity)
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
        run()
    }




    fun run() {
        val request = Request.Builder()
            .url("http://159.69.90.204/api/InfoApp/start.json")
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
                        val jsonObject = JSONTokener(response.body?.string()).nextValue() as JSONObject
                        //System.out.println(jsonObject.optString("header"))
                        var header = jsonObject.optString("header")
                        var text = jsonObject.optString("text")
                        runOnUiThread(Runnable {
                            val tv_header: TextView = findViewById(R.id.tv_header)
                            tv_header.text = header
                            val tv_text: TextView = findViewById(R.id.tv_text)
                            tv_text.text = text
                        })
                    }
                }
            }
        })
    }
}