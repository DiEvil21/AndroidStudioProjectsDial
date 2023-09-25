package ru.sport.netakoepril;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class LoadActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        RelativeLayout background = findViewById(R.id.load);


        ImageView imageView = findViewById(R.id.image_load);
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(
                imageView,
                "rotation",
                0f,
                360f
        );
        rotationAnimator.setDuration(10000);
        rotationAnimator.start();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 миллисекунд (3 секунды)
    }
}