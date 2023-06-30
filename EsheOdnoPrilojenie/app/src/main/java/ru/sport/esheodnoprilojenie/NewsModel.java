package ru.sport.esheodnoprilojenie;

public class NewsModel {
    private String imageUrl;
    private String title;
    private String shortText;
    private String fullText;

    // Конструктор, геттеры и сеттеры


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getShortText() {
        return shortText;
    }

    public String getFullText() {
        return fullText;
    }
}
