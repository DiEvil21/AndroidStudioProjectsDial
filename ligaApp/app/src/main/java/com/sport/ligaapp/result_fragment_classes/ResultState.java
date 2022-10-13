package com.sport.ligaapp.result_fragment_classes;

public class ResultState {
    String name, img2, date, img1, score;

    public ResultState(String name, String img2, String date, String img1, String score) {
        this.name = name;
        this.img2 = img2;
        this.date = date;
        this.img1 = img1;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public String getImg1() {
        return img1;
    }

    public String getImg2() {
        return img2;
    }

    public String getDate() {
        return date;
    }

    public String getScore() {
        return score;
    }
}
