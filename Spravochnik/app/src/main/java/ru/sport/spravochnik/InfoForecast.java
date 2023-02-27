package ru.sport.spravochnik;


public class InfoForecast {
    String header, about;

    public InfoForecast(String header, String about) {
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
