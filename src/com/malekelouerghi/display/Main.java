package com.malekelouerghi.display;

import com.malekelouerghi.core.BoardGame;
import com.malekelouerghi.thread.BoardThread;
import com.malekelouerghi.thread.FoodThread;
import com.malekelouerghi.thread.PigeonThread;
import com.malekelouerghi.thread.WalkerThread;

public class Main {


    public static void main(String[] args) {
        int numPigeons = 10;
        Display display = new Display(1024, 576); // WIDTH AND HEIGHT MUST BE BOTH GREATER (OR EQUAL) THAN 64
        BoardGame board = new BoardGame();
        Thread t1 = new Thread(new BoardThread(board), "Board Thread");
        t1.start();
        Thread t2 = new Thread(new FoodThread(board), "Food Thread");
        t2.start();
        ThreadGroup tg = new ThreadGroup("Pigeon Thread Group");
        for(int i = 0; i< numPigeons; i++){
            Thread t = new Thread(tg, new PigeonThread(board), "Pigeon Thread #" + (i+1));
            t.start();
        }
        Thread t3 = new Thread(new WalkerThread(board), "Walker Thread");
        t3.start();
        display.drawingLoop(board);
        t1.interrupt();
        t2.interrupt();
        tg.interrupt();
        t3.interrupt();
    }
}
