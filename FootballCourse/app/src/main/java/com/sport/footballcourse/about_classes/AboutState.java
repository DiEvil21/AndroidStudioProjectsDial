package com.sport.footballcourse.about_classes;

public class AboutState {
    private String header;
    private String date;
    private String text;
    private String image;

    public AboutState(String header, String text,String image){

        this.header = header;
        this.text = text;
        this.image = image;

    }
    public String getHeader() {
        return this.header;
    }
    public String getText() {
        return this.text;
    }
    public String getImage() {
        return this.image;
    }
}