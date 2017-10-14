package com.malekelouerghi.core;

public class Walker extends BoardObject {
    private int fearRadius;

    public Walker() {
        super();
        this.setFearRadius(50);
    }


    public int getFearRadius() {
        return fearRadius;
    }

    private void setFearRadius(int fearRadius) {
        this.fearRadius = fearRadius;
    }
}
