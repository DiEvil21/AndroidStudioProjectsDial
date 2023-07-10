package ru.sport.lolkekcheburek.model;

public class HockeyStatistic {
    private String player;
    private int goals;
    private int assists;

    // Геттеры и сеттеры

    public String getPlayer() {
        return player;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }
}

