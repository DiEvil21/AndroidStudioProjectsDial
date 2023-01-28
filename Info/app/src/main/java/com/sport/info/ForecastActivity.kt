package com.sport.info

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sport.info.forecasts.AdapterF
import com.sport.info.forecasts.ForecastData
import okhttp3.*
import org.json.JSONArray
import org.json.JSONTokener
import java.io.IOException

class ForecastActivity : AppCompatActivity() {
    private var client = OkHttpClient()
    val num = ArrayList<ForecastData>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        val linear: LinearLayout = findViewById(R.id.linear_forecast)
        run()
        Glide.with(this@ForecastActivity)
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


    fun setData(name: String,about: String) {
        num.add(ForecastData(name, about))
    }


    fun run() {
        val request = Request.Builder()
            .url("http://159.69.90.204/api/InfoApp/forecast.json")
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

                            //val question = jsonArray.getJSONObject(i).getString("header")
                            System.out.println(jsonArray.getJSONObject(i).optString("name"))
                            setData(jsonArray.getJSONObject(i).optString("name"),
                                jsonArray.getJSONObject(i).optString("about"))


                        }
                        runOnUiThread(Runnable {
                            val recyclerView: RecyclerView = findViewById(R.id.list)
                            recyclerView.adapter = AdapterF(num)
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        })
                    }
                }
            }
        })
    }
}