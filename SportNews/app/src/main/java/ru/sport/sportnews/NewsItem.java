package ru.sport.sportnews;

public class NewsItem {
    private String imageUrl;
    private String title;
    private String description;

    public NewsItem(String imageUrl, String title, String description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

