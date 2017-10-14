package com.malekelouerghi.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {
    @Test
    void addObject() {
        BoardGame board = new BoardGame();
        Pigeon pig = new Pigeon();
        board.addObject(pig);
        assertEquals( board.getBoard()[0][0],pig);
        Pigeon tmp = (Pigeon) board.getBoard()[0][0];
        assertEquals(tmp.getxPos(), 0);
        pig.setxPos(15);
        assertEquals(tmp.getxPos(), 15);
        assertEquals(board.getBoard()[1][0], null);
        Pigeon pig2 = new Pigeon(200,400);
        board.addObject(pig2);
        assertEquals(board.getBoard()[63][63], pig2);

    }

}