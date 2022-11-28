package com.sport.footballcalendar;

public class State {
    String team1, img2, date, img1, score, team2;

    public State(String team1,String team2, String img2, String date, String img1, String score) {
        this.team1 = team1;
        this.img2 = img2;
        this.date = date;
        this.img1 = img1;
        this.score = score;
        this.team2 = team2;

    }

    public String getTeam2() {
        return team2;
    }

    public String getTeam1() {
        return team1;
    }

    public String getImg1() {
        return img1;
    }

    public String getImg2() {
        return img2;
    }

    public String getDate() {
        return date;
    }

    public String getScore() {
        return score;
    }
}
