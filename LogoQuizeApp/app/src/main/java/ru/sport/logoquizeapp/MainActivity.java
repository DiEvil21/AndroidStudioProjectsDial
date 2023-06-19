package ru.sport.logoquizeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivity extends AppCompatActivity {
    private ImageView logoImageView;
    private EditText answerEditText;
    private Button submitButton;
    private ClubViewModel clubViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout backgroundLinearLayout = findViewById(R.id.main_back);
        String imageUrl = "http://159.69.90.204/api/LogoQuizeApp/background.png"; // Замените ссылку на вашу URL-ссылку на изображение
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                backgroundLinearLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
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
        logoImageView = findViewById(R.id.logoImageView);
        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.submitButton);

        clubViewModel = new ViewModelProvider(this).get(ClubViewModel.class);
        clubViewModel.getCurrentClubLiveData().observe(this, this::loadCurrentClub);

        submitButton.setOnClickListener(v -> checkAnswer());
        clubViewModel.getAllClubsResolvedLiveData().observe(this, allResolved -> {
            if (allResolved) {
                Toast.makeText(MainActivity.this, "All resolved!!!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadCurrentClub(Club club) {
        System.out.println(club.getLogoUrl());
        Picasso.get().load(club.getLogoUrl()).into(logoImageView);
    }

    private void checkAnswer() {
        String answer = answerEditText.getText().toString();
        clubViewModel.checkAnswer(answer);
        answerEditText.setText("");
    }
}
