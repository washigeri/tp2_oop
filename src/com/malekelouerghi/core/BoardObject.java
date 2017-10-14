package com.malekelouerghi.core;

public class BoardObject {
    private int xPos;
    private int yPos;

    public BoardObject() {
        this.setxPos(0);
        this.setyPos(0);
    }

    public BoardObject(int x, int y) {
        this.setxPos(x);
        this.setyPos(y);
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}
