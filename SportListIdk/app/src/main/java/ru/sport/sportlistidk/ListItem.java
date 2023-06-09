package ru.sport.sportlistidk;

public class ListItem {
    private String imageUrl;
    private String title;
    private String text;

    public ListItem(String imageUrl, String title, String text) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}

