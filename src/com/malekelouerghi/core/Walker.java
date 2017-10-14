package com.malekelouerghi.core;

public class Walker extends BoardObject {
    private int fearRadius;
    private BoardObject destination = null;


    public Walker(int x, int y, int fearRadius){
        super(x,y);
        this.setFearRadius(fearRadius);
    }


    public int getFearRadius() {
        return fearRadius;
    }

    private void setFearRadius(int fearRadius) {
        this.fearRadius = fearRadius;
    }

    public BoardObject getDestination() {
        return destination;
    }

    public void setDestination(BoardObject destination) {
        this.destination = destination;
    }
}
