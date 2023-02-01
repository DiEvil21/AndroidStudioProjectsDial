package ru.sport.liganewsapp.fragments.stats;

public class StatsState3 {
    String date, owner, score, quest;
    public StatsState3(String date, String owner, String score, String quest) {
        this.date = date;
        this.owner = owner;
        this.quest = quest;
        this.score = score;


    }

    public String getDate() {
        return date;
    }

    public String getScore() {
        return score;
    }

    public String getOwner() {
        return owner;
    }

    public String getQuest() {
        return quest;
    }
}
