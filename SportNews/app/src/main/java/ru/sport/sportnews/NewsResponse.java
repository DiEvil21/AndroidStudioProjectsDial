package ru.sport.sportnews;

import com.google.gson.annotations.SerializedName;

public class NewsResponse {
    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    // Геттеры и сеттеры

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
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

