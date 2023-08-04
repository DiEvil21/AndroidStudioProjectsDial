package com.sport.ktprila

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

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

            val drawable = BitmapDrawable(linearLayout.resources, result)
            linearLayout.background = drawable
        }
    }

}

