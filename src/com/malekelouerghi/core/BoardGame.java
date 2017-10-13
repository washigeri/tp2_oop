package com.malekelouerghi.core;

import static java.lang.Math.max;

public class BoardGame {
    private Object[][] board;
    private int objectsCount;


    public BoardGame() {
        this.setBoard(new Object[64][64]);
        this.setObjectsCount(0);
    }


    public boolean addObject(Object obj, int i, int j) {
        int MAXOBJECTS = 10;
        if (this.getObjectsCount() < MAXOBJECTS) {
            if (i > 63) {
                i = 63;
            }
            if (j > 63) {
                j = 63;
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
        if (i> 63|| j > 63)
            throw new ArrayIndexOutOfBoundsException("Parameters must be in range of array");
        else {
            this.getBoard()[i][j] = null;
            this.setObjectsCount(max(this.getObjectsCount() - 1, 0));
            return true;
        }

    }

    public Object[][] getBoard() {
        return board;
    }

    private void setBoard(Object[][] board) {
        this.board = board;
    }

    public int getObjectsCount() {
        return objectsCount;
    }

    private void setObjectsCount(int objectsCount) {
        this.objectsCount = objectsCount;
    }
}
