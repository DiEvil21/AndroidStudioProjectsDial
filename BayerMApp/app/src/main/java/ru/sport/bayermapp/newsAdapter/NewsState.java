package ru.sport.bayermapp.newsAdapter;

public class NewsState {
    private String header;
    private String date;
    private String text;
    private String image;

    public NewsState(String header, String date, String text,String image){

        this.header = header;
        this.date = date;
        this.text = text;
        this.image = image;

    }
    public String getHeader() {
        return this.header;
    }
    public String getDate() {
        return this.date;
    }
    public String getText() {
        return this.text;
    }
    public String getImage() {
        return this.image;
    }
}
