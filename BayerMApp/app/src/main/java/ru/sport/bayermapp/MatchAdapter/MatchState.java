package ru.sport.bayermapp.MatchAdapter;

public class MatchState {
    String name, role, country, img;

    public MatchState(String name, String role, String country, String img) {
        this.name = name;
        this.role = role;
        this.country = country;
        this.img = img;

    }


    public String getCountry() {
        return this.country;
    }


    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public String getImg() {
        return this.img;
    }
}

