package com.malekelouerghi.thread;

import com.malekelouerghi.core.BoardGame;
import com.malekelouerghi.core.Food;
import com.malekelouerghi.core.Pigeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PigeonThread implements Runnable {

    private Pigeon targetPigeon;
    private BoardGame board;

    public PigeonThread(BoardGame board) {
        this.setBoard(board);
        this.setTargetPigeon(this.createNewPigeon());
        this.board.addObject(this.getTargetPigeon());
    }


    private Pigeon createNewPigeon() {
        int randomY, randomX;
        do {
            randomY = ThreadLocalRandom.current().nextInt(0, 64);
            randomX = ThreadLocalRandom.current().nextInt(0, 64);
        } while (board.getBoard()[randomX][randomY] != null);
        return new Pigeon(randomX, randomY);
    }

    @Override
    public void run() {
        boolean doloop = true;
        long startTime;
        long endTime;
        long executionTime = 200L;
        while (doloop) {
            startTime = System.currentTimeMillis();
            this.getTargetPigeon().setTargetFood(this.findClosest());
            if (this.getTargetPigeon().getTargetFood() != null) {
                synchronized (this) {
                    Integer[] nextMovemnt = this.nextStep(this.getTargetPigeon().getTargetFood());
                    if (board.getBoard()[nextMovemnt[0]][nextMovemnt[1]] == null) {
                        this.movePigeon(nextMovemnt);
                    } else if (board.getBoard()[nextMovemnt[0]][nextMovemnt[1]] instanceof Food) {
                        this.eatFood(nextMovemnt);
                    }
                }
            }
            endTime = System.currentTimeMillis();
            try {
                Thread.sleep(Math.max(0, executionTime - (endTime - startTime)));
            } catch (InterruptedException e) {
                System.out.println("Stopping " + Thread.currentThread().getName());
                doloop = false;
            }
        }

    }

    private void movePigeon(Integer[] movementCoordinates) {
        try {
            if (this.getBoard().getBoard()[movementCoordinates[0]][movementCoordinates[1]] == null) {
                this.getTargetPigeon().setxPos(movementCoordinates[0]);
                this.getTargetPigeon().setyPos(movementCoordinates[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eatFood(Integer[] movementCoordinates) {
        try {
            if (!((Food) board.getBoard()[movementCoordinates[0]][movementCoordinates[1]]).isOld()) {
                this.targetPigeon.getTargetFood().setEaten(true);
                board.removeObject(movementCoordinates[0], movementCoordinates[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Integer[] nextStep(Food target) {
        int currentx = this.getTargetPigeon().getxPos();
        int currenty = this.getTargetPigeon().getyPos();
        float distance;
        HashMap<Float, Integer[]> neighborsDistance = new HashMap<>(9);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    distance = Float.MAX_VALUE;
                else if (currentx + i > 0 && currentx + i < board.getBoard().length &&
                        currenty + j > 0 && currenty + j < board.getBoard()[0].length
                        && (board.getBoard()[currentx + i][currenty + j] == null
                        || board.getBoard()[currentx + i][currenty + j] instanceof Food)) {
                    distance = (float) Math.sqrt(Math.pow(target.getxPos() - (currentx + i), 2) +
                            Math.pow(target.getyPos() - (currenty + j), 2));
                } else {
                    distance = Float.MAX_VALUE;
                }
                neighborsDistance.put(distance, new Integer[]{currentx + i, currenty + j});
            }
        }
        return neighborsDistance.get(Collections.min(neighborsDistance.keySet()));
    }

    private Food findClosest() {
        ArrayList<Food> results = this.findAllFoods();
        if (results.size() > 1) {
            float closest;
            float distance;
            HashMap<Float, Food> computedDitances = new HashMap<>();
            for (Food food : results
                    ) {
                distance = (float) Math.sqrt(Math.pow(food.getxPos() - this.getTargetPigeon().getxPos(), 2) + Math.pow(food.getyPos() - this.getTargetPigeon().getyPos(), 2));
                computedDitances.put(distance, food);
            }
            closest = Collections.min(computedDitances.keySet());
            return computedDitances.get(closest);
        } else if (results.size() == 1)
            return results.get(0);
        else
            return null;
    }

    private ArrayList<Food> findAllFoods() {
        int width = board.getBoard().length;
        int height = board.getBoard()[0].length;
        ArrayList<Food> res = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board.getBoard()[i][j] != null && board.getBoard()[i][j] instanceof Food
                        && !((Food) board.getBoard()[i][j]).isOld()
                        && !((Food) board.getBoard()[i][j]).isEaten()) {
                    res.add((Food) board.getBoard()[i][j]);
                }
            }
        }
        return res;
    }


    private Pigeon getTargetPigeon() {
        return targetPigeon;
    }

    private void setTargetPigeon(Pigeon targetPigeon) {
        this.targetPigeon = targetPigeon;
    }

    private BoardGame getBoard() {
        return this.board;
    }

    private void setBoard(BoardGame board) {
        this.board = board;
    }
}
