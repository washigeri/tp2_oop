package com.malekelouerghi.core;

public class Food extends BoardObject {
    private int ttl;
    private boolean old;
    private boolean eaten;

    public Food() {
        super();
        this.setTtl(10);
        this.setOld(false);
        this.setEaten(false);
    }

    public Food(int x, int y, int ttl) {
        super(x,y);
        this.setTtl(ttl);
        this.setEaten(false);
        this.setOld(false);
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

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}
