package com.malekelouerghi.display;

import com.malekelouerghi.core.BoardGame;
import com.malekelouerghi.core.Pigeon;
import sdljava.SDLException;

public class Main {

    public static void main(String[] args) {
        Display kek = new Display(1024, 576);
        Pigeon pig = new Pigeon(5, 5);
        BoardGame board = new BoardGame();
        board.addObject(pig, pig.getX(), pig.getY());
        kek.drawingLoop(board.getBoard());
    }
}
