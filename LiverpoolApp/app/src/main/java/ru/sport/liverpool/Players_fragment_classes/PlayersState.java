package ru.sport.liverpool.Players_fragment_classes;

public class PlayersState {
    String name, role, country, img, flag, info;

    public PlayersState(String name, String role, String country, String img, String flag, String info) {
        this.name = name;
        this.role = role;
        this.country = country;
        this.img = img;
        this.flag = flag;
        this.info = info;
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

    public String getInfo() {
        return info;
    }
}
