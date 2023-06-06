package ru.sport.ballgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class Ball {
    private Bitmap ballImage; // Изображение мяча
    private float x;
    private float y;
    private int radius;
    private float speedX;
    private float speedY;
    private float screenHeight;
    private float screenWidth;
    private float gravity;
    private float rotationAngle; // Угол вращения мяча
    private float rotationSpeed; // Скорость вращения мяча
    // Добавляем переменную для хранения последнего направления отскока
    private float lastBounceDirection;
    public Ball(float screenHeight, float screenWidth, float gravity, Context context) {
        x = screenWidth/2;
        y = screenHeight/2;
        radius = 100;
        speedX = 2;
        speedY = 2;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.gravity = gravity;
        rotationAngle = 0;
        rotationSpeed = 5;
        lastBounceDirection = 0;

        // Загрузка изображения мяча из ресурсов
        ballImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        // Масштабирование изображения мяча до заданного радиуса
        ballImage = Bitmap.createScaledBitmap(ballImage, radius * 2, radius * 2, false);
    }

    public void update() {
        // Обновление состояния мяча
        x += speedX;
        y += speedY;

        // Применение гравитации
        if (y + radius < screenHeight) {
            speedY += gravity; // Увеличение скорости по вертикали на величину гравитации
        } else {
            y = screenHeight - radius; // Мяч находится на земле и не прыгает
            speedY = 0;
        }
        // Обновление угла вращения
        rotationAngle += rotationSpeed * lastBounceDirection;
        if (x - radius < 0) {
            x = radius;
            speedX = -speedX;
        } else if (x + radius > screenWidth) {
            x = screenWidth - radius;
            speedX = -speedX;
        }

        if (y - radius < 0) {
            y = radius;
            speedY = -speedY;
        } else if (y + radius > screenHeight - radius) {
            y = screenHeight - radius - radius;
            speedY = -speedY;
        }
    }

    public void draw(Canvas canvas) {
        rotationAngle += rotationSpeed * lastBounceDirection; // Изменяем направление вращения

        Matrix matrix = new Matrix();
        matrix.postRotate(rotationAngle, radius, radius);
        matrix.postTranslate(x - radius, y - radius);

        canvas.drawBitmap(ballImage, matrix, null);
    }

    public boolean isTouched(float touchX, float touchY) {
        // Проверка, был ли мяч каснут пользователем
        float distance = (float) Math.sqrt(Math.pow(touchX - x, 2) + Math.pow(touchY - y, 2));
        return distance <= radius;
    }

    public void bounce(float touchX, float touchY) {
        // Изменение свойств мяча после набивки
        speedY = -10; // Задайте желаемую скорость отскока

        // Определение направления отскока в зависимости от стороны касания
        float dx = touchX - x;
        float dy = touchY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Вычисление угла отскока
        float angle = (float) Math.atan2(dy, dx);

        // Задание скорости по горизонтали и вертикали
        float speed = 20; // Задайте желаемую скорость
        speedX = -speed * (float) Math.cos(angle);
        speedY = -speed * (float) Math.sin(angle);
        lastBounceDirection = (touchX > x) ? 1 : -1;
    }

    public float getY() {
        return  y;
    }
    public float getRadius() {
        return radius;
    }
}

