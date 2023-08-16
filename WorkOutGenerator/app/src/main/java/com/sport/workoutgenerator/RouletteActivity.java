package com.sport.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class RouletteActivity extends AppCompatActivity {
    private RouletteViewModel viewModel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);
        viewModel = new ViewModelProvider(this).get(RouletteViewModel.class);

        Button startButton = findViewById(R.id.start_button);
        ImageView imageView = findViewById(R.id.image_view);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.startRoulette();
            }
        });

        viewModel.getImageData().observe(this, new Observer<ImageData>() {
            @Override
            public void onChanged(ImageData imageData) {
                // Загрузка изображения с помощью Picasso
                Picasso.get().load(imageData.getImageUrl()).into(imageView);

            }
        });

        RouletteRepository repository = RouletteRepository.getInstance();
        repository.getImageData().observe(this, new Observer<List<ImageData>>() {
            @Override
            public void onChanged(List<ImageData> imageDataList) {
                viewModel.setImageList(imageDataList);
            }
        });
    }
}
