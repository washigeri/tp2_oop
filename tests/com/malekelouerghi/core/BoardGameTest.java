package com.malekelouerghi.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {
    @Test
    void addObject() {
        BoardGame board = new BoardGame();
        Pigeon pig = new Pigeon();
        board.addObject(pig, (short)0,(short)0);
        assertEquals( board.getBoard()[0][0],pig);
        Pigeon tmp = (Pigeon) board.getBoard()[0][0];
        assertEquals(tmp.getX(), 0);
        pig.setX(15);
        assertEquals(tmp.getX(), 15);
        assertEquals(board.getBoard()[1][0], null);

    }

}