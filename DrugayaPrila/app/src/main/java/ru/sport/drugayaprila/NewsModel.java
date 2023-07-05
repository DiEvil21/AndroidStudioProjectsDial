package ru.sport.drugayaprila;

public class NewsModel {
    private String title;
    private String description;
    private String imageUrl;

    // Конструктор, геттеры и сеттеры

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

