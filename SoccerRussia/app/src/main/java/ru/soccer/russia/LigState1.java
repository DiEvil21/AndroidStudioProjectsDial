package ru.soccer.russia;

public class LigState1 {
    String left, right, imgLeft, imgRight;
    public LigState1(String left, String right, String imgLeft, String imgRight) {
        this.left = left;
        this.right = right;
        this.imgLeft = imgLeft;
        this.imgRight = imgRight;



    }

    public String getImgLeft() {
        return imgLeft;
    }

    public String getImgRight() {
        return imgRight;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
}
