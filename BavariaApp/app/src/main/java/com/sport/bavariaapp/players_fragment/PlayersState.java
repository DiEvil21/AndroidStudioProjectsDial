package com.sport.bavariaapp.players_fragment;

public class PlayersState {
    String name, role, country, img, flag;

    public PlayersState(String name, String role, String country, String img, String flag) {
        this.name = name;
        this.role = role;
        this.country = country;
        this.img = img;
        this.flag = flag;
    }


    public String getCountry() {
        return this.country;
    }

    public String getFlag() {
        return this.flag;
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
