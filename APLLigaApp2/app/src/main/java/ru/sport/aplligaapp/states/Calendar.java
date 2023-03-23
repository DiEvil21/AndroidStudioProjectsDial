package ru.sport.aplligaapp.states;


public class Calendar {
    String date, owner, score, quest;
    public Calendar(String date, String owner, String score, String quest) {
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