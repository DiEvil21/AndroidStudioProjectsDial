package com.sport.sportfitnessapp;


public class Exercise {
    private String name;
    private String imageUrl;

    public Exercise(String name, String imageUrl) {
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

