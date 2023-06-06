package ru.sport.ballgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Bitmap exitButtonBitmap;
    private Ball ball;
    private float screenWidth;
    private float screenHeight;
    private int score;
    private  float exitButtonX;
    private  float exitButtonY;
    // Координаты предыдущего касания
    private float lastTouchX;
    private float lastTouchY;
    private Bitmap background; // Изображение заднего фона
    private Bitmap labelImage; // Изображение фона счетчика
    private Rect labelRect; // Прямоугольник для позиционирования фона счетчика
    private Button exitButton;
    private ViewGroup viewGroup;

    public GameView(Context context, RelativeLayout container) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        ball = new Ball(screenHeight,screenWidth, 0.8f,context);
        score = 0;
        exitButtonBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_image);
        // Загрузка изображения заднего фона
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(background, (int) screenWidth, (int) screenHeight, false);

        // Загрузка изображения фона счетчика
        labelImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.label);
        int labelWidth = (int) (screenWidth / 2); // Ширина фона счетчика
        int labelHeight = (int) (labelImage.getHeight() * labelWidth / labelImage.getWidth()); // Пропорциональная высота фона счетчика
        labelImage = Bitmap.createScaledBitmap(labelImage, labelWidth, labelHeight, false);

        // Позиционирование фона счетчика посередине экрана сверху
        int labelX = (int) ((screenWidth - labelWidth) / 2);
        int labelY = 50; // Вертикальное смещение фона счетчика от верхнего края экрана
        labelRect = new Rect(labelX, labelY, labelX + labelWidth, labelY + labelHeight);






    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Обработка изменений размеров игрового экрана
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();
            if (isExitButtonTouched(touchX, touchY)) {
                // Кнопка "Выход" была нажата
                // Открыть MenuActivity
                Intent intent = new Intent(getContext(), MenuActivity.class);
                getContext().startActivity(intent);
            } else {
                if (ball.isTouched(touchX, touchY)) {
                    // Касание было рядом с мячом, откидываем мяч
                    ball.bounce(touchX, touchY);
                    increaseScore();
                }
            }
            lastTouchX = touchX;
            lastTouchY = touchY;
            return true;
        }
        return false;
    }

    // Метод для проверки, было ли касание на кнопке "Выход"
    private boolean isExitButtonTouched(float touchX, float touchY) {
        return touchX >= exitButtonX && touchX <= exitButtonX + exitButtonBitmap.getWidth() &&
                touchY >= exitButtonY && touchY <= exitButtonY + exitButtonBitmap.getHeight();
    }

    public void update() {
        // Обновление состояния игры
        ball.update();
        // Проверка столкновения мяча с нижней границей экрана
        if (ball.getY() + ball.getRadius() >= screenHeight -100) {
            // Мяч коснулся нижней границы экрана, сброс счетчика попаданий
            score = 0;

        }

    }

    public void draw(Canvas canvas) {
        // Отрисовка игры
        super.draw(canvas);
        if (canvas != null) {

            // Отрисовка заднего фона
            canvas.drawBitmap(background, 0, 0, null);
            // Отрисовка мяча
            ball.draw(canvas);
            // Отрисовка фона счетчика
            canvas.drawBitmap(labelImage, null, labelRect, null);
            // Отрисовка счетчика попаданий
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(180);
            float scoreX = labelRect.centerX() - 50 ; // Позиционирование счетчика по центру по горизонтали
            float scoreY = labelRect.centerY() + paint.getTextSize() / 2 + 50; // Позиционирование счетчика по центру по вертикали
            canvas.drawText("" + score, scoreX, scoreY, paint);
            // Отрисовка кнопки "Выход"
            int exitButtonWidth = 300;  // Установите нужную ширину кнопки
            int exitButtonHeight = 120;  // Установите нужную высоту кнопки
            exitButtonX = 20;
            exitButtonY = 20;
            paint.setColor(Color.WHITE);
            exitButtonBitmap = Bitmap.createScaledBitmap(exitButtonBitmap, exitButtonWidth, exitButtonHeight, false);
            canvas.drawBitmap(exitButtonBitmap, exitButtonX, exitButtonY, null);
        }
    }
    // Метод для увеличения счетчика попаданий
    public void increaseScore() {
        score++;
    }
}

