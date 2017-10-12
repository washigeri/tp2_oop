package com.malek.elouerghi;

public class Food {
    private int xPos;
    private int yPos;
    private int ttl;
    private boolean old;

    public Food() {
        this.setxPos(0);
        this.setyPos(0);
        this.setTtl(10);
        this.setOld(false);
    }

    public Food(int x, int y, int ttl) {
        this.setxPos(x);
        this.setyPos(y);
        this.setTtl(ttl);
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

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public boolean isOld() {
        return old;
    }

    public void setOld(boolean old) {
        this.old = old;
    }
}
