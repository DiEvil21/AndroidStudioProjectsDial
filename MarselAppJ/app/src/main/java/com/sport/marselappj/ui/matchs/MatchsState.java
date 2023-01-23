package com.sport.marselappj.ui.matchs;

public class MatchsState {
    String date, country, name, score, img;
    public MatchsState(String img, String date, String country, String name, String score) {
        this.date = date;
        this.country = country;
        this.name = name;
        this.score = score;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getCountry() {
        return country;
    }
}
