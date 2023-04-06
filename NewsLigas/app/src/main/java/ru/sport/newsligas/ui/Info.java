package ru.sport.newsligas.ui;

import android.text.Spanned;

public class Info {
    private String header;
    private String date;
    private Spanned text;
    private String image;

    public Info(String header, String date, Spanned text, String image){

        this.header = header;
        this.date = date;
        this.text = text;
        this.image = image;

    }
    public String getHeader() {
        return this.header;
    }
    public String getDate() {
        return this.date;
    }
    public Spanned getText() {
        return this.text;
    }
    public String getImage() {
        return this.image;
    }
}

