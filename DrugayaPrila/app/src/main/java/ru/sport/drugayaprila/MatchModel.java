package ru.sport.drugayaprila;

public class MatchModel {
    private String team1;
    private String team2;
    private String score;

    // Конструктор, геттеры и сеттеры

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getScore() {
        return score;
    }
}
