package com.malekelouerghi.thread;

import com.malekelouerghi.core.BoardGame;
import com.malekelouerghi.core.Food;

import java.util.ArrayList;
import java.util.Iterator;

public class FoodThread implements Runnable {
    private BoardGame board;
    private ArrayList<Food> foodList;

    public FoodThread(BoardGame board) {
        this.setBoard(board);
        this.setFoodList(new ArrayList<>());
    }


    @Override
    public void run() {
        boolean doloop = true;
        long startTime;
        long endTime;
        long executionTime = 200L;
        while (doloop) {
            startTime = System.currentTimeMillis();
            this.addNewFoodsToList();
            this.updateFoodsState();
            this.updateFoodList();
            endTime = System.currentTimeMillis();
            try {
                Thread.sleep(Math.max(0, executionTime - (endTime - startTime)));
            } catch (InterruptedException e) {
                System.out.println("Stopping " + Thread.currentThread().getName());
                doloop = false;
            }
        }

    }

    void addNewFoodsToList() {
        int width = this.getBoard().getBoard().length;
        int height = this.getBoard().getBoard()[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (this.getBoard().getBoard()[i][j] instanceof Food) {
                    Food food = (Food) this.getBoard().getBoard()[i][j];
                    if (!food.isOld()) {
                        if (!this.getFoodList().contains(food)) {
                            this.getFoodList().add(food);
                        }
                    }
                }
            }
        }
    }

    void updateFoodList() {
        this.getFoodList().removeIf(Food::isEaten);
    }

    void updateFoodsState() {
        Iterator<Food> iter = this.getFoodList().iterator();
        while (iter.hasNext()) {
            Food f = iter.next();
            if (!f.isOld()) {
                if (f.getTtl() > 0) {
                    f.setTtl(f.getTtl() - 1);
                } else if (f.getTtl() == 0) {
                    f.setOld(true);
                }
            } else {
                if (f.getTtl() == -20) {
                    this.getBoard().removeObject(f.getxPos(), f.getyPos());

                    iter.remove();
                } else {
                    f.setTtl(f.getTtl() - 1);
                }
            }

        }
    }

    private BoardGame getBoard() {
        return board;
    }

    private void setBoard(BoardGame board) {
        this.board = board;
    }

    ArrayList<Food> getFoodList() {
        return foodList;
    }

    private void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }
}
