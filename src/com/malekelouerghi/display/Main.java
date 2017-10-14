package com.malekelouerghi.display;

import com.malekelouerghi.core.BoardGame;
import com.malekelouerghi.thread.BoardThread;
import com.malekelouerghi.thread.PigeonThread;

public class Main {


    public static void main(String[] args) {
        int numPigeons = 10;
        Display display = new Display(1024, 576);
        BoardGame board = new BoardGame();
        Thread t1 = new Thread(new BoardThread(board), "Board Thread");
        t1.start();
        ThreadGroup tg = new ThreadGroup("Pigeon Thread Group");
        for(int i = 0; i< numPigeons; i++){
            Thread t = new Thread(tg, new PigeonThread(board), "Pigeon Thread #" + (i+1));
            t.start();
        }

        display.drawingLoop(board);
        t1.interrupt();
        tg.interrupt();
    }
}
