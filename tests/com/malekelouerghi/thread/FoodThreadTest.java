package com.malekelouerghi.thread;

import com.malekelouerghi.core.BoardGame;
import com.malekelouerghi.core.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodThreadTest {
    @Test
    void updateFoodList() {
        FoodThread ft = new FoodThread(null);
        Food f = new Food(10,10,30);
        ft.getFoodList().add(f);
        assertEquals(ft.getFoodList().size(), 1);
        f.setEaten(true);
        assertEquals(ft.getFoodList().size(), 1);
        ft.updateFoodList();
        assertEquals(ft.getFoodList().size(), 0);
    }

    @Test
    void updateFoodsState() {
        BoardGame board = new BoardGame();
        Food f = new Food(5,5,30);
        board.addObject(f);
        FoodThread ft = new FoodThread(board);
        ft.addNewFoodsToList();
        assertEquals(1, ft.getFoodList().size());
        ft.updateFoodsState();
        assertEquals(29, ft.getFoodList().get(0).getTtl());
        f.setTtl(0);
        ft.updateFoodsState();
        assertEquals(true, ft.getFoodList().get(0).isOld());
        f.setTtl(-20);
        ft.updateFoodsState();
        assertEquals(0, ft.getFoodList().size());

    }

    @Test
    void addNewFoodsToList() {
        BoardGame board = new BoardGame();
        FoodThread ft = new FoodThread(board);
        Food f1 = new Food(0,0,30);
        Food f2 = new Food(10,10,30);
        board.addObject(f1);
        board.addObject(f2);
        assertEquals(board.getObjectsCount(), 2);
        ft.addNewFoodsToList();
        assertEquals(ft.getFoodList().size(), 2);
        assertEquals(ft.getFoodList().get(0), f1);
        f1.setxPos(10);
        assertEquals(ft.getFoodList().get(0).getxPos(), 10);
        f1.setEaten(true);
        assertEquals(ft.getFoodList().get(0).isEaten(), true);
    }

}