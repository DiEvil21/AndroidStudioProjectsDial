package ru.sport.lolkekcheburek.model;

public class HockeyMatch {
    private String team1;
    private String team2;
    private String score;

    // Геттеры и сеттеры

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getScore() {
        return score;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

