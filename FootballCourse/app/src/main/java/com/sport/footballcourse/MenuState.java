package com.sport.footballcourse;

public class MenuState {
    String header, url, img;

    public MenuState(String header, String url, String img) {
        this.header = header;
        this.url = url;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getHeader() {
        return header;
    }

    public String getUrl() {
        return url;
    }
}
