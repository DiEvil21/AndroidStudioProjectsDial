package ru.sport.footballrules;

public class State {
    String header, about;

    public State(String header, String about) {
        this.about = about;
        this.header = header;
    }

    public String getAbout() {
        return about;
    }

    public String getHeader() {
        return header;
    }
}
