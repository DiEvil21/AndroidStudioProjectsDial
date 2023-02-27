package ru.sport.aplligaapp.fragments.calendar;


public class CalendarState {
    String date, owner, score, quest;
    public CalendarState(String date, String owner, String score, String quest) {
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

