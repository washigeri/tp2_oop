package com.malekelouerghi.core;

public class Walker {
    private int posX;
    private int posY;
    private int fearRadius;

    public Walker(){
        this.setPosX(0);
        this.setPosY(0);
        this.setFearRadius(50);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getFearRadius() {
        return fearRadius;
    }

    public void setFearRadius(int fearRadius) {
        this.fearRadius = fearRadius;
    }
}
