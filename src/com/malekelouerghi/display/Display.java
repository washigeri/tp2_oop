package com.malekelouerghi.display;

import com.malekelouerghi.core.*;
import sdljava.SDLException;
import sdljava.SDLMain;
import sdljava.event.SDLEvent;
import sdljava.event.SDLKey;
import sdljava.event.SDLKeyboardEvent;
import sdljava.event.SDLMouseButtonEvent;
import sdljava.video.SDLRect;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;

import static java.lang.Math.max;

public class Display {
    private SDLSurface screen;
    private SDLSurface pigeonTile;
    private SDLSurface foodTile;
    private SDLSurface walkerTile;
    private SDLSurface oldFoodTile;
    private int tilesizew;
    private int tilesizeh;

    Display() {
        this.init(512, 288);
    }

    Display(int width, int height) {
        width = max(64, width);
        height = max(64, height);
        this.tilesizeh = height / 64;
        this.tilesizew = width / 64;
        this.init(width, height);
    }

    private void init(int width, int height) {
        try {

            SDLMain.init(SDLMain.SDL_INIT_VIDEO);
            this.setScreen(SDLVideo.setVideoMode(width, height, 32, SDLVideo.SDL_HWSURFACE | SDLVideo.SDL_DOUBLEBUF));
            SDLVideo.wmSetCaption("Pigeon Square SDL", null);
            getScreen().fillRect(getScreen().mapRGB(0, 0, 0));
            getScreen().flip();
            this.setPigeonTile(SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, tilesizew, tilesizeh, 32, 0, 0, 0, 0));
            this.getPigeonTile().fillRect(this.getScreen().mapRGB(255, 0, 0));
            this.setFoodTile(SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, tilesizew, tilesizeh, 32, 0, 0, 0, 0));
            this.getFoodTile().fillRect(this.getScreen().mapRGB(255, 255, 0));
            this.setWalkerTile(SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, tilesizew, tilesizeh, 32, 0, 0, 0, 0));
            this.getWalkerTile().fillRect(this.getScreen().mapRGB(255, 255, 255));
            this.setOldFoodTile(SDLVideo.createRGBSurface(SDLVideo.SDL_HWSURFACE, tilesizew, tilesizeh, 32, 0, 0, 0, 0));
            this.getOldFoodTile().fillRect(this.getScreen().mapRGB(0, 0, 255));
        } catch (SDLException e) {
            System.out.print(e.getMessage());
            System.exit(-1);
        }
    }

    void drawingLoop(BoardGame board) {

        boolean loop = true;
        SDLEvent event;
        long startTime;
        long endTime;
        int framerate = 10;
        float frametime = (1f / framerate) * 1000;
        startTime = System.currentTimeMillis() / 1000;
        while (loop) try {
            event = SDLEvent.pollEvent();
            if (event != null) {
                switch (event.getType()) {
                    case SDLEvent.SDL_QUIT:
                        loop = false;
                        break;
                    case SDLEvent.SDL_KEYDOWN:
                        SDLKeyboardEvent eventKeyboard = (SDLKeyboardEvent) event;
                        if (eventKeyboard.getSym() == SDLKey.SDLK_ESCAPE) {
                            loop = false;
                        }
                        break;
                    case SDLEvent.SDL_MOUSEBUTTONUP:
                        SDLMouseButtonEvent eventMouse = (SDLMouseButtonEvent) event;
                        if (eventMouse.getButton() == SDLEvent.SDL_BUTTON_LEFT || eventMouse.getButton() == SDLEvent.SDL_BUTTON_RIGHT) {
                            int x = this.mapScreentoBoard(eventMouse.getX(), 'w');
                            int y = this.mapScreentoBoard(eventMouse.getY(), 'h');
                            board.addObject(new Food(x, y, 30));

                        }
                        break;
                }
            }
            getScreen().fillRect(getScreen().mapRGB(0, 0, 0));
            this.drawBoard(board);
            this.getScreen().flip();
            endTime = System.currentTimeMillis();
            Thread.sleep(Math.max((long) (frametime - (endTime - startTime)), 0));

        } catch (SDLException e) {
            e.printStackTrace();
            loop = false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawBoard(final BoardGame boardgame) {
        SDLRect pos = new SDLRect();
        final BoardObject[][] board = boardgame.getBoard();
        int width = board.length;
        int height = board[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j] != null) {
                    pos.setX(this.mapBoardtoScreen(i, 'w'));
                    pos.setY(this.mapBoardtoScreen(j, 'h'));
                    if (board[i][j] instanceof Pigeon) {
                        try {
                            this.getPigeonTile().blitSurface(this.getScreen(), pos);
                        } catch (SDLException e) {
                            e.printStackTrace();
                        }
                    } else if (board[i][j] instanceof Food) {
                        try {
                            if (!((Food) board[i][j]).isOld())
                                this.getFoodTile().blitSurface(this.getScreen(), pos);
                            else
                                this.getOldFoodTile().blitSurface(this.getScreen(), pos);
                        } catch (SDLException e) {
                            e.printStackTrace();
                        }
                    } else if (board[i][j] instanceof Walker) {
                        try {
                            this.getWalkerTile().blitSurface(this.getScreen(), pos);
                        } catch (SDLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    int mapScreentoBoard(int pos, char mode) {
        int res = 0;
        if (mode == 'w') {
            res = (64) * (pos) / (this.getScreen().getWidth());
        } else if (mode == 'h') {
            res = (64) * (pos) / (this.getScreen().getHeight());
        }
        return res;
    }

    private int mapBoardtoScreen(int pos, char mode) {
        int res = 0;
        if (mode == 'w') {
            res = (this.getScreen().getWidth()) * (pos) / (64);
        } else if (mode == 'h') {
            res = (this.getScreen().getHeight()) * pos / 64;
        }
        return res;
    }

    private SDLSurface getScreen() {
        return screen;
    }

    private void setScreen(SDLSurface screen) {
        this.screen = screen;
    }

    private SDLSurface getPigeonTile() {
        return pigeonTile;
    }

    private void setPigeonTile(SDLSurface pigeonTile) {
        this.pigeonTile = pigeonTile;
    }

    private SDLSurface getFoodTile() {
        return foodTile;
    }

    private void setFoodTile(SDLSurface foodTile) {
        this.foodTile = foodTile;
    }

    private SDLSurface getWalkerTile() {
        return walkerTile;
    }

    private void setWalkerTile(SDLSurface walkerTile) {
        this.walkerTile = walkerTile;
    }

    @Override
    protected void finalize() throws Throwable {
        this.getFoodTile().freeSurface();
        this.getPigeonTile().freeSurface();
        this.getWalkerTile().freeSurface();
        this.getScreen().freeSurface();
        SDLMain.quit();
    }


    private SDLSurface getOldFoodTile() {
        return oldFoodTile;
    }

    private void setOldFoodTile(SDLSurface oldFoodTile) {
        this.oldFoodTile = oldFoodTile;
    }
}
