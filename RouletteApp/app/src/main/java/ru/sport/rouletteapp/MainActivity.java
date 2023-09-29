package ru.sport.rouletteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private float currentAngle = 0.0f;
    private String selected_color = null;
    private TextView tv_coins, tv_bet_count;
    private int bet = 0,bank, winBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RelativeLayout background = findViewById(R.id.main_relative);
        String imageUrl = "http://159.69.90.204/api/RouletteApp/background.jpg"; // Замените ссылку на вашу URL-ссылку на изображение
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                background.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                // Обработка ошибки загрузки изображения
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // Действия при подготовке загрузки изображения
            }
        });




        Button btn_rot = findViewById(R.id.btn_rot);
        Button btn_red = findViewById(R.id.btn_red);
        Button btn_green = findViewById(R.id.btn_green);
        Button btn_dark = findViewById(R.id.btn_dark);
        Button btn_bet = findViewById(R.id.btn_bet);
        Button btn_p100 = findViewById(R.id.btn_p100);
        Button btn_p200 = findViewById(R.id.btn_p200);
        Button btn_m100 = findViewById(R.id.btn_m100);
        Button btn_m200 = findViewById(R.id.btn_m200);
        tv_bet_count = findViewById(R.id.bet_count);
        tv_coins = findViewById(R.id.tv_coins);
        btn_m100.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                bet -=100;
                if (bet <0) {bet = 0;}
                tv_bet_count.setText(bet + "");
            }
        });
        btn_m200.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                bet -=200;
                if (bet <0) {bet = 0;}
                tv_bet_count.setText(bet + "");
            }
        });
        btn_p100.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                bet +=100;
                tv_bet_count.setText(bet + "");
            }
        });
        btn_p200.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                bet +=200;
                tv_bet_count.setText(bet + "");
            }
        });

        btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_color = "red";
                // Установите подчеркивание для нажатой кнопки
                btn_red.setBackgroundResource(R.drawable.btn_selected);
                // Уберите подчеркивание с остальных кнопок
                btn_green.setBackgroundResource(R.drawable.btn_deselected);
                btn_dark.setBackgroundResource(R.drawable.btn_deselected);
            }
        });

        btn_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_color = "green";
                // Установите подчеркивание для нажатой кнопки
                btn_green.setBackgroundResource(R.drawable.btn_selected);
                // Уберите подчеркивание с остальных кнопок
                btn_red.setBackgroundResource(R.drawable.btn_deselected);
                btn_dark.setBackgroundResource(R.drawable.btn_deselected);
            }
        });

        btn_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_color = "black";
                // Установите подчеркивание для нажатой кнопки
                btn_dark.setBackgroundResource(R.drawable.btn_selected);
                // Уберите подчеркивание с остальных кнопок
                btn_red.setBackgroundResource(R.drawable.btn_deselected);
                btn_green.setBackgroundResource(R.drawable.btn_deselected);
            }
        });

        btn_bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateWheelToRandomAngle();
            }
        });
    }




    private void rotateWheelToRandomAngle() {
        bank = Integer.parseInt(tv_coins.getText().toString());
        bet = Integer.parseInt(tv_bet_count.getText().toString());
        if (selected_color == null) {
            Toast.makeText(this,"Select the color", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bet == 0) {
            Toast.makeText(this,"Need bet", Toast.LENGTH_SHORT).show();
            return;
        }else if (bet > bank) {
            Toast.makeText(this,"Need more coins for this bet", Toast.LENGTH_SHORT).show();
            return;
        }
        ImageView rouletteWheel = findViewById(R.id.iv_roulette);
        Random random = new Random();
        int randomAngle = 1000 + random.nextInt(2000);

        RotateAnimation rotateAnimation = new RotateAnimation(
                currentAngle, currentAngle + randomAngle,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        currentAngle += randomAngle;
        rouletteWheel.startAnimation(rotateAnimation);

        String color = "green";
        float rounded = currentAngle % 360;

        if (((180 <= rounded) && (rounded <= 192)) || ((0 <= rounded) && (rounded <= 12))) {
            color = "green";
        } else if (((int) (rounded / 12) % 2) == 0) {
            color = "red";
        } else {
            color = "black";
        }
        bank -= bet;
        winBank = bank;
        if (color.equals(selected_color)) {
            if (color.equals("green")) {
                winBank += bet*35;
            }else {
                winBank += bet*2;
            }
        }

        Log.d("KEK", String.valueOf(winBank));

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationStart(Animation animation) {

                tv_coins.setText(bank+ "");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("KEK","animation end");
                setBank();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Log.d("KEK", color);

    }

    @SuppressLint("SetTextI18n")
    private void setBank() {
        tv_coins.setText(winBank+"");
    }
}