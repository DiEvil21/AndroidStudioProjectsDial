package ru.sport.memorytiles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private List<Button> buttons;
    private List<Integer> imageIds;
    private Button flippedButton;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackground();
        gridLayout = findViewById(R.id.gridLayout);
        buttons = new ArrayList<>();
        imageIds = new ArrayList<>();
        flippedButton = null;
        score = 0;

        createButtonGrid();
        setupRestartButton();

        Collections.shuffle(imageIds);
    }

    private void createButtonGrid() {
        int gridSize = 16; // Размер сетки (количество кнопок)
        int rows = 4; // Количество строк
        int columns = 4; // Количество столбцов

        gridLayout.setColumnCount(columns);
        gridLayout.setRowCount(rows);

        for (int i = 0; i < gridSize; i++) {
            Button button = new Button(this);
            button.setLayoutParams(new GridLayout.LayoutParams());
            button.setPadding(8, 8, 8, 8);
            button.setMaxWidth(100);
            button.setMaxHeight(100);
            button.setBackgroundResource(R.drawable.card_bg);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 200;
            params.height = 200;
            button.setLayoutParams(params);

            final int index = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flipButton(button, index);
                }
            });

            buttons.add(button);
            gridLayout.addView(button);
        }

        // Заполнение списка imageIds
        for (int i = 0; i < gridSize / 2; i++) {
            imageIds.add(getResources().getIdentifier("image" + (i + 1), "drawable", getPackageName()));
            imageIds.add(getResources().getIdentifier("image" + (i + 1), "drawable", getPackageName()));
        }
    }

    private void setupRestartButton() {
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private List<Button> flippedButtons = new ArrayList<>();

    private void flipButton(Button button, int index) {
        if (flippedButtons.contains(button)) {
            // Нельзя кликнуть на ту же самую кнопку
            return;
        }

        flipCard(button, imageIds.get(index));
        flippedButtons.add(button);

        if (flippedButtons.size() == 2) {
            Button button1 = flippedButtons.get(0);
            Button button2 = flippedButtons.get(1);

            if (button1.getTag(R.id.tag_image_id).equals(button2.getTag(R.id.tag_image_id))) {
                // Найдена пара
                System.out.println("найдена пара");
                removeButtons(button1, button2);
                updateScore(1);
                checkGameOver();
                flippedButtons.clear();
            } else {
                // Не совпадает, перевернуть обратно
                disableAllButtons();
                button1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flipCard(button1, R.drawable.card_bg);
                        flipCard(button2, R.drawable.card_bg);
                        flippedButtons.remove(button1);
                        flippedButtons.remove(button2);
                        enableAllButtons();
                    }
                }, 1000);
            }
        }
    }

    private void flipCard(Button button, int imageId) {
        Animation flipInAnimation = AnimationUtils.loadAnimation(this, R.anim.flip_in);
        Animation flipOutAnimation = AnimationUtils.loadAnimation(this, R.anim.flip_out);

        flipOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Начало анимации flip_out
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Конец анимации flip_out
                button.setBackgroundResource(imageId);
                button.setTag(R.id.tag_image_id, imageId);
                button.setTag(R.id.tag_is_front, true);
                button.startAnimation(flipInAnimation);
                removeButtonsAfterAnimation(button);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        button.startAnimation(flipOutAnimation);
    }

    private void removeButtonsAfterAnimation(Button button) {
        if (flippedButtons.size() == 2) {
            Button button1 = flippedButtons.get(0);
            Button button2 = flippedButtons.get(1);

            if (button1.getTag(R.id.tag_image_id).equals(button2.getTag(R.id.tag_image_id))) {
                // Найдена пара
                System.out.println("найдена пара");
                removeButtons(button1, button2);
                updateScore(1);
                checkGameOver();
                flippedButtons.clear();
            } else {
                // Не совпадает, перевернуть обратно
                disableAllButtons();
                button1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flipCard(button1, R.drawable.card_bg);
                        flipCard(button2, R.drawable.card_bg);
                        flippedButtons.remove(button1);
                        flippedButtons.remove(button2);
                        enableAllButtons();
                    }
                }, 1000);
            }
        }
    }

    private void removeButtons(Button button1, Button button2) {
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button1.setTag(R.id.tag_is_removed, true);
        button2.setTag(R.id.tag_is_removed, true);
    }

    private void updateScore(int increment) {
        score += increment;
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(getString(R.string.score_format, score));
    }

    private void checkGameOver() {
        boolean gameFinished = true;
        for (Button button : buttons) {
            if (button.getVisibility() != View.INVISIBLE) {
                gameFinished = false;
                break;
            }
        }

        if (gameFinished) {
            Button restartButton = findViewById(R.id.restartButton);
            restartButton.setVisibility(View.VISIBLE);
        }
    }

    private void resetGame() {
        for (Button button : buttons) {
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
            button.setTag(R.id.tag_image_id, null);
            button.setTag(R.id.tag_is_removed, false);
            button.setBackgroundResource(R.drawable.card_bg);
        }
        flippedButton = null;
        score = 0;

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(getString(R.string.score_format, score));

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setVisibility(View.INVISIBLE);

        Collections.shuffle(imageIds);
    }

    private void disableAllButtons() {
        for (Button button : buttons) {
            if (!Boolean.TRUE.equals(button.getTag(R.id.tag_is_removed))) {
                button.setEnabled(false);
            }
        }
    }

    private void enableAllButtons() {
        for (Button button : buttons) {
            button.setEnabled(true);
        }
    }


    private void setBackground() {
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ConstraintLayout back = findViewById(R.id.main_back);
        Glide.with(MainActivity.this)
                .load("http://159.69.90.204/api/MemoryTiles/background.png")
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        back.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}






