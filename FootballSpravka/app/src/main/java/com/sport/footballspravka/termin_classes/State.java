package com.sport.footballspravka.termin_classes;

public class State {
    String termin, about;
    public State(String termin, String about) {
        this.termin = termin;
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public String getTermin() {
        return termin;
    }
}
