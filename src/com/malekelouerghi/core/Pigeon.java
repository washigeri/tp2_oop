package com.malekelouerghi.core;

public class Pigeon extends BoardObject {
    private Food targetFood;

    Pigeon() {
        super();
        this.setTargetFood(null);
    }

    public Pigeon(int x, int y) {
        super(x, y);
        this.setTargetFood(null);
    }


    public Food getTargetFood() {
        return targetFood;
    }

    public void setTargetFood(Food targetFood) {
        this.targetFood = targetFood;
    }
}
