package com.sport.bavariaapp.result_fragment;

public class ResultState {
    String name, role, country, img, score;

    public ResultState(String name, String role, String country, String img, String score) {
        this.name = name;
        this.role = role;
        this.country = country;
        this.img = img;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getRole() {
        return role;
    }

    public String getCountry() {
        return country;
    }

    public String getScore() {
        return score;
    }
}

