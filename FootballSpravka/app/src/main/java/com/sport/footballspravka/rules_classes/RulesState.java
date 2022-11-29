package com.sport.footballspravka.rules_classes;

public class RulesState {
    String header, text, img;

    public RulesState(String header, String text, String img){

        this.header = header;
        this.text = text;
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }

    public String getHeader() {
        return this.header;
    }

    public String getText() {
        return this.text;
    }
}
