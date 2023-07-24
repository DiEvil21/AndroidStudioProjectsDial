package ru.sport.hockeyquize;

public class QuestionData {

    private String question;
    private Boolean answer;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public Boolean getAnswer() {
        return answer;
    }
}
