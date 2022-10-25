package com.sport.scorecounter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    LinearLayout bmImage;
    Drawable d;
    Bitmap mIcon11 = null;

    public DownloadImageTask(LinearLayout bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        if (mIcon11 == null) {
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }else return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        d = null;
        Context context = this.bmImage.getContext();
        d = new BitmapDrawable(context.getResources(), result);
        System.out.println("-------------------------пришло");

        bmImage.setBackground(d);
    }
}

