package com.malekelouerghi.thread;

import com.malekelouerghi.core.BoardGame;
import com.malekelouerghi.core.BoardObject;

public class BoardThread implements Runnable {

    private BoardGame gameBoard;

    public BoardThread(BoardGame gameBoard) {
        this.setGameBoard(gameBoard);
    }

    @Override
    public void run() {
        boolean doloop = true;
        long startTime;
        long endTime;
        long executionTime = 200L;
        boolean updateStatus;
        while (doloop) {
            startTime = System.currentTimeMillis();
            updateStatus = this.updateBoard();
            if (!updateStatus) {
                doloop = false;
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

    private boolean updateBoard() {
        boolean res = true;
        int width = this.getGameBoard().getBoard().length;
        int height = this.getGameBoard().getBoard()[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (this.getGameBoard().getBoard()[i][j] != null) {
                    BoardObject obj = this.getGameBoard().getBoard()[i][j];
                    if (obj.getxPos() != i || obj.getyPos() != j) {
                        boolean a, b;
                        a = this.getGameBoard().removeObject(i, j);
                        b = this.getGameBoard().addObject(obj);
                        if (!a || !b) {
                            obj.setxPos(i);
                            obj.setyPos(j);
                            b = this.getGameBoard().addObject(obj);
                        }
                        res = res && a;
                        res = res && b;
                    }
                }
            }
        }
        return res;
    }

    private BoardGame getGameBoard() {
        return gameBoard;
    }

    private void setGameBoard(BoardGame gameBoard) {
        this.gameBoard = gameBoard;
    }
}
