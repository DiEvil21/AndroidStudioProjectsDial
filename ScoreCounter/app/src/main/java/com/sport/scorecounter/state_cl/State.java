package com.sport.scorecounter.state_cl;

public class State {
    String score, time;

    public State(String score, String time) {
        this.score = score;
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }
}
