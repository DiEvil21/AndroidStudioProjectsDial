package com.sport.noobsfitnessapp;

public class SelectState {
    String header, about, img, url;

    public SelectState (String header, String about, String img,String url) {
        this.header = header;
        this.about = about;
        this.img = img;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public String getHeader() {
        return header;
    }

    public String getAbout() {
        return about;
    }
}

