package com.malekelouerghi.thread;

import com.malekelouerghi.core.BoardGame;
import com.malekelouerghi.core.BoardObject;
import com.malekelouerghi.core.Walker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class WalkerThread implements Runnable {

    private Walker walker;
    private BoardGame board;
    private boolean readyToWalk;

    public WalkerThread(BoardGame board) {
        this.setBoard(board);
        this.setWalker(new Walker(-1, -1, 20));
        this.setReadyToWalk(false);
    }

    private char setStartingpoint() {
        int side = ThreadLocalRandom.current().nextInt(0, 1);
        int randX, randY;
        char res;
        if (side == 0) {
            //START ON ONE OF THE 'X' BORDERS
            int sideY = ThreadLocalRandom.current().nextInt(0, 1);
            randY = (sideY == 0) ? 0 : (this.getBoard().getBoard()[0].length);
            do {
                randX = ThreadLocalRandom.current().nextInt(0, this.getBoard().getBoard().length);
            } while (this.getBoard().getBoard()[randX][randY] != null);
            if (randY == 0) {
                res = 'U';
            } else {
                res = 'D';
            }
        } else {
            //START ON ONE OF THE 'Y' BORDERS
            int sideX = ThreadLocalRandom.current().nextInt(0, 1);
            randX = (sideX == 0) ? 0 : (this.getBoard().getBoard().length - 1);
            do {
                randY = ThreadLocalRandom.current().nextInt(0, this.getBoard().getBoard()[0].length);
            } while (this.getBoard().getBoard()[randX][randY] != null);
            if (randX == 0) {
                res = 'L';
            } else {
                res = 'R';
            }
        }
        this.getWalker().setxPos(randX);
        this.getWalker().setyPos(randY);
        return res;
    }

    private BoardObject getEndPoint(char start) {
        char dest;
        ArrayList<Character> allDests = new ArrayList<>(4);
        allDests.add('L');
        allDests.add('U');
        allDests.add('R');
        allDests.add('D');
        Predicate<Character> destPredicate = p -> p == start;
        allDests.removeIf(destPredicate);
        dest = WalkerThread.getRandomDest(allDests);
        int randX = -1, randY = -1;
        switch (dest) {
            case 'L':
                randX = 0;
                break;
            case 'R':
                randX = this.getBoard().getBoard().length - 1;
                break;
            case 'U':
                randY = 0;
                break;
            case 'D':
                randY = this.getBoard().getBoard()[0].length - 1;
                break;
        }
        if (randX == -1) {
            do {
                randX = ThreadLocalRandom.current().nextInt(0, this.getBoard().getBoard().length);
            } while (this.getBoard().getBoard()[randX][randY] != null);
        } else {
            do {
                randY = ThreadLocalRandom.current().nextInt(0, this.getBoard().getBoard()[0].length);
            } while (this.getBoard().getBoard()[randX][randY] != null);
        }
        return new BoardObject(randX, randY);
    }

    private static char getRandomDest(ArrayList<Character> possibleDest) {
        return possibleDest.get(ThreadLocalRandom.current().nextInt(possibleDest.size()));
    }


    @Override
    public void run() {
        boolean doloop = true;
        long startTime;
        long endTime;
        long executionTime = 200L;
        int thresh, value;
        int counter = 0;
        final int counterThresh = 10;
        while (doloop) {
            startTime = System.currentTimeMillis();
            thresh = ThreadLocalRandom.current().nextInt(0, 101);
            value = ThreadLocalRandom.current().nextInt(0, 26);
            if (value > thresh && !this.isReadyToWalk()) {
                if (counter > counterThresh) {
                    char start = this.setStartingpoint();
                    BoardObject dest = this.getEndPoint(start);
                    this.getWalker().setDestination(dest);
                    this.setReadyToWalk(true);
                    this.getBoard().addObject(this.getWalker());
                    this.getBoard().setWalkerPresent(true);
                    counter = 0;
                } else {
                    counter++;
                }
            }
            if (this.isReadyToWalk()) {
                Integer[] nextMovement = this.nextStep(this.getWalker().getDestination());
                if (this.getWalker().getDestination().getxPos() == nextMovement[0] && this.getWalker().getDestination().getyPos() == nextMovement[1]) {
                    this.doneWalking();
                } else if (this.getBoard().getBoard()[nextMovement[0]][nextMovement[1]] == null) {
                    this.moveWalker(nextMovement);
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

    private void moveWalker(Integer[] movementCoordinates) {
        try {
            if (this.getBoard().getBoard()[movementCoordinates[0]][movementCoordinates[1]] == null) {
                this.getWalker().setxPos(movementCoordinates[0]);
                this.getWalker().setyPos(movementCoordinates[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doneWalking() {
        try {
            this.getBoard().removeObject(this.getWalker().getxPos(), this.getWalker().getyPos());
            this.getBoard().setWalkerPresent(false);
            this.getWalker().setDestination(null);
            this.getWalker().setxPos(-1);
            this.getWalker().setyPos(-1);
            this.setReadyToWalk(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer[] nextStep(BoardObject target) {
        int currentx = this.getWalker().getxPos();
        int currenty = this.getWalker().getyPos();
        float distance;
        HashMap<Float, Integer[]> neighborsDistance = new HashMap<>(9);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    distance = Float.MAX_VALUE;
                else if (currentx + i >= 0 && currentx + i < board.getBoard().length &&
                        currenty + j >= 0 && currenty + j < board.getBoard()[0].length
                        && (board.getBoard()[currentx + i][currenty + j] == null)) {
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

    private Walker getWalker() {
        return walker;
    }

    private void setWalker(Walker walker) {
        this.walker = walker;
    }

    private BoardGame getBoard() {
        return board;
    }

    private void setBoard(BoardGame board) {
        this.board = board;
    }

    private boolean isReadyToWalk() {
        return readyToWalk;
    }

    private void setReadyToWalk(boolean readyToWalk) {
        this.readyToWalk = readyToWalk;
    }
}
