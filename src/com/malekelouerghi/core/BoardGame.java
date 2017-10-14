package com.malekelouerghi.core;

import static java.lang.Math.max;

public class BoardGame {
    private BoardObject[][] board;
    private int objectsCount;
    private boolean isWalkerPresent;


    public BoardGame() {
        this.setBoard(new BoardObject[64][64]);
        this.setObjectsCount(0);
        this.setWalkerPresent(false);
    }


    public boolean addObject(BoardObject obj) {

        int i = obj.getxPos(), j = obj.getyPos();
        int MAXOBJECTS = 20;
        if (this.getObjectsCount() < MAXOBJECTS) {
            if (i > 63) {
                i = 63;
                obj.setxPos(i);
            }
            if (j > 63) {
                j = 63;
                obj.setyPos(j);
            }
            if (this.getBoard()[i][j] == null) {
                this.getBoard()[i][j] = obj;
                this.setObjectsCount(this.getObjectsCount() + 1);
                return true;
            } else
                return false;
        }
        return false;
    }

    public boolean removeObject(int i, int j) throws ArrayIndexOutOfBoundsException {
        if (i > 63 || j > 63)
            throw new ArrayIndexOutOfBoundsException("Parameters must be in range of array");
        else {
            this.getBoard()[i][j] = null;
            this.setObjectsCount(max(this.getObjectsCount() - 1, 0));
            return true;
        }

    }

    public BoardObject[][] getBoard() {
        return board;
    }

    private void setBoard(BoardObject[][] board) {
        this.board = board;
    }

    public int getObjectsCount() {
        return objectsCount;
    }

    private void setObjectsCount(int objectsCount) {
        this.objectsCount = objectsCount;
    }

    public boolean isWalkerPresent() {
        return isWalkerPresent;
    }

    public void setWalkerPresent(boolean walkerPresent) {
        isWalkerPresent = walkerPresent;
    }
}
