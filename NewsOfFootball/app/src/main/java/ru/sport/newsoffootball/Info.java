package ru.sport.newsoffootball;

public class Info {
    String head;
    String img;
    String date;
    String text;
    public  Info(String head, String img, String date, String text) {
        this.date = date;
        this.head = head;
        this.img = img;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getImg() {
        return img;
    }

    public String getHead() {
        return head;
    }

    public String getText() {
        return text;
    }
}
