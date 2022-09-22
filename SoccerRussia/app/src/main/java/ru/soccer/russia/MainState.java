package ru.soccer.russia;

public class MainState {
    String name, text, img, textRight;

    public MainState(String name,String text,String textRight,String img) {
        this.name = name;
        this.text = text;
        this.img = img;
        this.textRight = textRight;

    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getTextR() {
        return textRight;
    }
}
