package ru.sport.footballdictionary;

public class Data {
    String word, meaning;

    public Data(String word,String menaing) {
        this.word = word;
        this.meaning = menaing;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getWord() {
        return word;
    }
}
