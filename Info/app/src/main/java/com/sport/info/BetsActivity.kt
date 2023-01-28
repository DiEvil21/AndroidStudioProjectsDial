package com.sport.info

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sport.info.bets.Adapter
import com.sport.info.bets.BetsData
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class BetsActivity : AppCompatActivity() {
    private var client = OkHttpClient()
    val num = ArrayList<BetsData>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bets)
        val recyclerView: RecyclerView = findViewById(R.id.list)
        val linear: LinearLayout = findViewById(R.id.linear_bets)
        Glide.with(this@BetsActivity)
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

        recyclerView.adapter = Adapter(num)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    fun setData(name: String,img: String,about: String) {
        num.add(BetsData(name,img, about))
    }

    fun run() {
        val request = Request.Builder()
            .url("http://159.69.90.204/api/InfoApp/bets.json")
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
                        val jsonArray = JSONTokener(jsonObject.getString("bets")).nextValue() as JSONArray
                        for (i in 0 until jsonArray.length()) {

                            //val question = jsonArray.getJSONObject(i).getString("header")
                            System.out.println(jsonArray.getJSONObject(i).optString("name"))
                            setData(jsonArray.getJSONObject(i).optString("name"),
                                    jsonArray.getJSONObject(i).optString("img"),
                                    jsonArray.getJSONObject(i).optString("about"))


                        }
                        runOnUiThread(Runnable {
                            val recyclerView: RecyclerView = findViewById(R.id.list)
                            recyclerView.adapter = Adapter(num)
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        })
                    }
                }
            }
        })
    }
}