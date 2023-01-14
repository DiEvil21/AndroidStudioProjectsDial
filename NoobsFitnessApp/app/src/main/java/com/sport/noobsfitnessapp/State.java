package com.sport.noobsfitnessapp;

public class State {
    String img, name, kol, rep;
    public State(String img, String name, String kol, String rep) {
        this.img = img;
        this.name = name;
        this.kol = kol;
        this.rep = rep;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getKol() {
        return kol;
    }

    public String getRep() {
        return rep;
    }
}
