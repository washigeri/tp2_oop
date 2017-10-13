package com.malekelouerghi.display;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisplayTest {
    @Test
    void mapScreentoBoard() {
        Display disp = new Display();
        assertEquals(disp.mapScreentoBoard(511,'w'), 63);
        assertEquals(disp.mapScreentoBoard(256, 'w'), 32);
        assertEquals(disp.mapScreentoBoard(287,'h'), 63);
        assertEquals(disp.mapScreentoBoard(144, 'h'), 32);
        assertNotEquals(disp.mapScreentoBoard(512, 'w'), 63);

        try {
            disp.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}