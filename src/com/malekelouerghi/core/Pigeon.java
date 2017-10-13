package com.malekelouerghi.core;

public class Pigeon {
    private int x;
    private int y;
    private Food targetFood;

    public Pigeon() {
        this.setX(0);
        this.setY(0);
        this.setTargetFood(null);
    }

    public Pigeon(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setTargetFood(null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Food getTargetFood() {
        return targetFood;
    }

    public void setTargetFood(Food targetFood) {
        this.targetFood = targetFood;
    }
}
