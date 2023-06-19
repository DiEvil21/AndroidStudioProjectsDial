package ru.sport.logoquizeapp;

public class Club {
    private String name;
    private String logoUrl;

    public Club(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}

