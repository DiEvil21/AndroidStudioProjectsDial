package ru.sport.clubquize;

public class Club {
    private String name;
    private String imageUrl;

    public Club(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

